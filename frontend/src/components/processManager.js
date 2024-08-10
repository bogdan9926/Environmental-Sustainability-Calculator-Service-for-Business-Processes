import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import AnnotationsUploadComponent from './fileUpload';
import ProcessTable from './processTable';
import BPMNViewerComponent from './BPMNViewerComponent';
import AnnotationEditor from './AnnotationEditor';
import './processManager.css';
import axios from "axios";
import EmissionFactorsUploadComponent from "./emissionFactorsUpload";
import EmissionFactorEditor from "./EmissionFactorEditor";
import DownloadReport from './DownloadReport';
import ErrorLogWindow from './errorLogWindow'; // Import the ErrorLogWindow component
import { Tooltip } from 'react-tooltip'; // Import Tooltip

const ProcessManager = () => {
    const [processData, setProcessData] = useState([]);
    const [selectedProcess, setSelectedProcess] = useState('');
    const [selectedInstance, setSelectedInstance] = useState('');
    const [diagramXML, setDiagramXML] = useState('');
    const [annotations, setAnnotations] = useState([]);
    const [emissionFactors, setEmissionFactors] = useState([]);
    const [errors, setErrors] = useState([]); // State to keep track of errors

    useEffect(() => {
        const socketUrl = 'http://localhost:5000/ws';
        const sock = new SockJS(socketUrl);
        const stompClient = new Client({
            webSocketFactory: () => sock,
            debug: (str) => {
                console.log(str);
            },
            reconnectDelay: 10000,
            onConnect: () => {
                console.log('Connected to STOMP server.');
                stompClient.subscribe('/topic/messages', (message) => {
                    const messageBody = JSON.parse(message.body);
                    console.log('Received message:', messageBody);
                    setProcessData(prevData => [...prevData, messageBody]);
                });
                stompClient.subscribe('/topic/emissions', (message) => {
                    const initialData = JSON.parse(message.body);
                    console.log('Received initial data:', initialData);
                    setProcessData(initialData);
                    const firstProcess = initialData[0]?.processName;
                    const firstInstance = initialData.find(data => data.processName === firstProcess)?.processInstance;
                    const firstDiagramXML = initialData.find(data => data.processName === firstProcess)?.diagramXML;
                    if (firstProcess) {
                        setSelectedProcess(firstProcess);
                    }
                    if (firstInstance) {
                        setSelectedInstance(firstInstance);
                    }
                    if (firstDiagramXML) {
                        setDiagramXML(firstDiagramXML);
                    }
                });
                stompClient.subscribe('/topic/annotations', (message) => {
                    const annotationsData = JSON.parse(message.body);
                    console.log('Received annotations:', annotationsData);
                    setAnnotations(annotationsData);
                });
                stompClient.subscribe('/topic/emissionFactors', (message) => {
                    const emissionFactors = JSON.parse(message.body);
                    console.log('Received emissionFactors:', emissionFactors);
                    setEmissionFactors(emissionFactors);
                });
            },
            onStompError: (frame) => {
                const errorMessage = `STOMP error: ${frame.headers['message']}, Detail: ${frame.body}`;
                console.error(errorMessage);
                setErrors(prevErrors => [...prevErrors, errorMessage]);
            },
            onWebSocketError: (event) => {
                const errorMessage = `WebSocket error: ${event.reason || event.message || event}`;
                console.error(errorMessage);
                setErrors(prevErrors => [...prevErrors, errorMessage]);
            },
            onWebSocketClose: (event) => {
                const errorMessage = `WebSocket closed: ${event.reason || event.code}`;
                console.log(errorMessage);
                setErrors(prevErrors => [...prevErrors, errorMessage]);
            }
        });

        stompClient.activate();

        return () => {
            stompClient.deactivate();
        };
    }, []);

    const handleFileSubmit = async (jsonText) => {
        let url = process.env.REACT_APP_API_URL_ANNOTATIONS;
        const formData = new FormData();
        formData.append('annotations', jsonText);
        try {
            const response = await axios.post(url, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            return response; // Return the response for further processing
        } catch (error) {
            const errorMessage = `File submit error: ${error.message}`;
            console.error(errorMessage);
            setErrors(prevErrors => [...prevErrors, errorMessage]);
            throw error; // Rethrow the error to be handled by the caller
        }
    };

    const handleEmissionFactorSubmit = async (emissionFactor) => {
        let url = process.env.REACT_APP_API_URL_FUEL_EMISSIONS;
        const formData = new FormData();
        const emissionFactorsList = [emissionFactor];

        // Serialize the list of emission factors as a JSON string
        const jsonText = JSON.stringify(emissionFactorsList);

        formData.append('factors', jsonText);
        console.log(jsonText)
        try {
            const response = await axios.post(url, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            return response; // Return the response for further processing
        } catch (error) {
            const errorMessage = `Emission factor submit error: ${error.message}`;
            console.error(errorMessage);
            setErrors(prevErrors => [...prevErrors, errorMessage]);
            throw error; // Rethrow the error to be handled by the caller
        }
    };

    const handleProcessChange = (processName) => {
        setSelectedProcess(processName);
        setSelectedInstance(''); // Reset instance selection when process changes
        const selectedProcessData = processData.find(data => data.processName === processName);
        if (selectedProcessData && selectedProcessData.diagramXML) {
            setDiagramXML(selectedProcessData.diagramXML);
        } else {
            setDiagramXML('');
        }
    };

    const handleInstanceChange = (instance) => {
        setSelectedInstance(instance);
    };

    const filteredTasks = processData.filter(
        (data) =>
            data.processName === selectedProcess && data.processInstance === selectedInstance
    );

    const uniqueProcesses = Array.from(new Set(processData.map((data) => data.processName)));
    const uniqueInstances = Array.from(
        new Set(
            processData
                .filter((data) => data.processName === selectedProcess)
                .map((data) => data.processInstance)
        )
    );

    return (
        <div className="process-manager">
            <div className="upload-edit-container">
                <div className="row">
                    <div data-tooltip-id="my-tooltip" data-tooltip-content="Upload annotations here">
                        <AnnotationsUploadComponent onSubmit={handleFileSubmit} />
                    </div>
                    <div data-tooltip-id="my-tooltip" data-tooltip-content="Upload fuel emission factors here">
                        <EmissionFactorsUploadComponent onSubmit={handleEmissionFactorSubmit} />
                    </div>
                </div>
                <div className="row">
                    <div data-tooltip-id="my-tooltip" data-tooltip-content="Edit annotations directly in the editor">
                        <AnnotationEditor
                            annotations={annotations}
                            setAnnotations={setAnnotations}
                            onSubmit={handleFileSubmit}
                        />
                    </div>
                    <div data-tooltip-id="my-tooltip" data-tooltip-content="Edit emission factors directly in the editor">
                        <EmissionFactorEditor
                            emissionFactors={emissionFactors}
                            setEmissionFactors={setEmissionFactors}
                            onSubmit={handleEmissionFactorSubmit}
                        />
                    </div>
                </div>
            </div>
            {uniqueProcesses.length > 0 ? (
                <div className="process-selection">
                    <div className="process-buttons">
                        <h2>Processes</h2>
                        {uniqueProcesses.map((process, index) => (
                            <button
                                key={index}
                                onClick={() => handleProcessChange(process)}
                                className={selectedProcess === process ? 'active' : ''}
                                data-tooltip-id="my-tooltip"
                                data-tooltip-content={`View information for process ${process}`}
                            >
                                {process}
                            </button>
                        ))}
                    </div>
                    {uniqueInstances.length > 0 && selectedProcess ? (
                        <div className="instance-selection">
                            <h3>Instances</h3>
                            {uniqueInstances.map((instance, index) => (
                                <button
                                    key={index}
                                    onClick={() => handleInstanceChange(instance)}
                                    className={selectedInstance === instance ? 'active' : ''}
                                    data-tooltip-id="my-tooltip"
                                    data-tooltip-content={`View information for instance ${instance}`}
                                >
                                    Instance {instance}
                                </button>
                            ))}
                            <ProcessTable tasks={filteredTasks} />
                            <DownloadReport
                                tasks={processData}
                                selectedProcess={selectedProcess}
                                selectedInstance={selectedInstance}
                                diagramXML={diagramXML}
                            />
                        </div>
                    ) : (
                        <div className="no-instances">No instances available</div>
                    )}
                </div>
            ) : (
                <div className="no-processes">No processes available</div>
            )}
            {diagramXML && (
                <div className="diagram-container">
                    <h3>Process Diagram</h3>
                    <BPMNViewerComponent diagramXML={diagramXML} />
                </div>
            )}
            {errors.length > 0 && <ErrorLogWindow errors={errors} />} {/* Conditionally render the ErrorLogWindow component */}
            <Tooltip id="my-tooltip" />
        </div>
    );
};

export default ProcessManager;
