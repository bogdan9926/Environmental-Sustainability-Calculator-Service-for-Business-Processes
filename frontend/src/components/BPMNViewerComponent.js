import React, { useEffect, useRef } from 'react';
import BpmnViewer from 'bpmn-js/lib/NavigatedViewer';

const BPMNViewerComponent = ({ diagramXML }) => {
    const viewerRef2 = useRef(null);
    const bpmnViewer = useRef(null);

    useEffect(() => {
        bpmnViewer.current = new BpmnViewer({
            container: viewerRef2.current,
            /* uncomment to configure defaults for all overlays
            overlays: {
              defaults: {
                show: { minZoom: 1 },
                scale: true
              }
            }
            */
        });

        return () => {
            if (bpmnViewer.current) {
                bpmnViewer.current.destroy();
            }
        };
    }, []);

    useEffect(() => {
        if (bpmnViewer.current && diagramXML) {
            bpmnViewer.current.importXML(diagramXML).then(() => {
                const canvas = bpmnViewer.current.get('canvas');
                const overlays = bpmnViewer.current.get('overlays');

                // zoom to fit full viewport
                canvas.zoom('fit-viewport');

                // attach an overlay to a node


            }).catch((err) => {
                console.error('could not import BPMN 2.0 diagram', err);
            });
        }
    }, [diagramXML]);

    return <div style={{ width: '100%', height: '600px' }} ref={viewerRef2}></div>;
};

export default BPMNViewerComponent;
