import React, { useState } from 'react';
import Modal from 'react-modal';
import AceEditor from 'react-ace';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-github';
import './annotationEditor.css';

// Make sure to bind modal to your appElement (http://reactcommunity.org/react-modal/accessibility/).
Modal.setAppElement('#root');

const AnnotationEditor = ({ annotations, setAnnotations, onSubmit }) => {
    const [selectedAnnotation, setSelectedAnnotation] = useState(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setSelectedAnnotation(null);
        setModalIsOpen(false);
    };

    const handleAnnotationSubmit = async () => {
        const updatedAnnotation = annotations[selectedAnnotation];
        await onSubmit(updatedAnnotation.data);
        closeModal();
    };

    const handleAnnotationChange = (newData) => {
        const updatedAnnotations = [...annotations];
        updatedAnnotations[selectedAnnotation].data = newData;
        setAnnotations(updatedAnnotations);
    };

    const handleSelectorChange = (e) => {
        setSelectedAnnotation(parseInt(e.target.value, 10));
    };

    return (
        <div className="annotation-editor">
            <button onClick={openModal} className="edit-annotations-btn">Edit Annotations</button>
            <Modal
                className="modal"
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Edit Annotation"
            >
                <h3>Edit Annotations</h3>
                <div className="input-group">
                    <label>Process Name:</label>
                    <select onChange={handleSelectorChange} value={selectedAnnotation ?? ''}>
                        <option value="" disabled>Select an Annotation</option>
                        {annotations.map((annotation, index) => (
                            <option key={index} value={index}>
                                {annotation.processName}
                            </option>
                        ))}
                    </select>
                </div>
                {selectedAnnotation !== null && (
                    <>
                        <AceEditor
                            mode="json"
                            theme="github"
                            name="annotationEditor"
                            value={annotations[selectedAnnotation].data}
                            onChange={handleAnnotationChange}
                            editorProps={{ $blockScrolling: true }}
                            setOptions={{ useWorker: false }}
                            wrapEnabled={true}
                            width="100%"
                            height="400px"
                        />
                        <button className="submit-button" onClick={handleAnnotationSubmit}>Submit</button>
                    </>
                )}
                <button className="close-button" onClick={closeModal}>Close</button>
            </Modal>
        </div>
    );
};

export default AnnotationEditor;
