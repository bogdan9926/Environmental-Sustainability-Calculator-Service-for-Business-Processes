import React, { useEffect, useRef, useState } from 'react';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { format, isValid, parseISO } from 'date-fns';
import BpmnViewer from 'bpmn-js/lib/NavigatedViewer';
import html2canvas from 'html2canvas';

const DownloadReport = ({ tasks, selectedProcess, selectedInstance, diagramXML }) => {
    const viewerRef = useRef(null);
    const hiddenViewerRef = useRef(null);
    const bpmnViewer = useRef(null);
    const hiddenBpmnViewer = useRef(null);
    const [diagramReady, setDiagramReady] = useState(false);

    useEffect(() => {
        bpmnViewer.current = new BpmnViewer({
            container: viewerRef.current,
        });

        hiddenBpmnViewer.current = new BpmnViewer({
            container: hiddenViewerRef.current,
        });

        return () => {
            if (bpmnViewer.current) {
                bpmnViewer.current.destroy();
            }
            if (hiddenBpmnViewer.current) {
                hiddenBpmnViewer.current.destroy();
            }
        };
    }, []);

    useEffect(() => {
        if (bpmnViewer.current && diagramXML) {
            bpmnViewer.current.importXML(diagramXML).then(() => {
                const canvas = bpmnViewer.current.get('canvas');
                canvas.zoom('fit-viewport');
            }).catch((err) => {
                console.error('could not import BPMN 2.0 diagram', err);
            });
        }
    }, [diagramXML]);

    useEffect(() => {
        if (hiddenBpmnViewer.current && diagramXML) {
            hiddenBpmnViewer.current.importXML(diagramXML).then(() => {
                const canvas = hiddenBpmnViewer.current.get('canvas');
                canvas.zoom('fit-viewport');
                setDiagramReady(true);
            }).catch((err) => {
                console.error('could not import BPMN 2.0 diagram', err);
                setDiagramReady(false);
            });
        }
    }, [diagramXML]);

    const handleDownload = async () => {
        const processTasks = tasks.filter(
            (task) => task.processName === selectedProcess && task.processInstance === selectedInstance
        );

        const doc = new jsPDF();

        // Add title and some styling
        doc.setFontSize(18);
        doc.text('CO2 Emission Report', 14, 22);
        doc.setFontSize(12);
        doc.setTextColor(100);
        doc.text(`Process: ${selectedProcess}`, 14, 30);
        doc.text(`Instance: ${selectedInstance}`, 14, 36);

        // Define the table columns and data
        const tableColumn = ["Task Name", "CO2 Emission Value", "Start Time", "End Time"];
        const tableRows = [];

        let totalTask = null;

        processTasks.forEach(task => {
            const taskData = [
                task.taskName,
                `${task.co2EmissionValue} kg`,
                task.startTime ? (isValid(parseISO(task.startTime)) ? format(parseISO(task.startTime), 'PPpp') : 'Invalid date') : 'No date provided',
                task.endTime ? (isValid(parseISO(task.endTime)) ? format(parseISO(task.endTime), 'PPpp') : 'Invalid date') : 'No date provided'
            ];
            if (task.taskName.toLowerCase() === "total") {
                totalTask = taskData;
            } else {
                tableRows.push(taskData);
            }
        });

        // Add regular tasks table to the PDF
        autoTable(doc, {
            startY: 50,
            head: [tableColumn],
            body: tableRows,
        });

        // Add total task summary
        let diagramStartY = doc.lastAutoTable.finalY + 20;
        if (totalTask) {
            const pageWidth = doc.internal.pageSize.getWidth();
            const startY = doc.lastAutoTable.finalY + 20;

            doc.setFontSize(16);
            doc.setTextColor(0);
            doc.setFont('helvetica', 'bold');
            doc.setDrawColor(0);
            doc.setFillColor(211, 211, 211);

            doc.rect(14, startY, pageWidth - 28, 30, 'FD'); // Filled and draw
            doc.text('Total CO2 Emissions:', 18, startY + 20);
            doc.text(totalTask[1], pageWidth - 18, startY + 20, { align: 'right' });

            diagramStartY = startY + 40; // Adjust start position for the diagram
        }

        // Add BPMN diagram header
        const pageHeight = doc.internal.pageSize.getHeight();
        const margin = 10;
        const diagramHeight = 200; // Approximate height of the diagram

        if (diagramStartY + diagramHeight + margin > pageHeight) {
            doc.addPage();
            diagramStartY = margin;
        }

        doc.setFontSize(14);
        doc.text('BPMN Diagram:', 14, diagramStartY);

        // Convert BPMN diagram to PNG and add to PDF
        if (diagramReady) {
            await html2canvas(hiddenViewerRef.current).then((canvas) => {
                const dataUrl = canvas.toDataURL('image/png');
                const pdfWidth = doc.internal.pageSize.getWidth() - 20;
                const diagramHeight = (canvas.height * pdfWidth) / canvas.width;
                if (diagramStartY + diagramHeight + margin > pageHeight) {
                    doc.addPage();
                    diagramStartY = margin;
                }
                doc.addImage(dataUrl, 'PNG', 10, diagramStartY + 10, pdfWidth, diagramHeight);
            }).catch((error) => {
                console.error('Error generating image from canvas', error);
            });
        }

        // Save the PDF
        doc.save(`process_${selectedProcess}_instance_${selectedInstance}_report.pdf`);
    };

    return (
        <>
            <button onClick={handleDownload} disabled={!selectedProcess || !selectedInstance}>
                Download CO2 Report
            </button>
            <div ref={hiddenViewerRef} style={{ width: '100%', height: '600px', position: 'absolute', left: '-9999px' }}></div>
        </>
    );
};

export default DownloadReport;
