import React, { useState } from 'react';
import Modal from 'react-modal';
import AceEditor from 'react-ace';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-github';
import './fileUpload.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

Modal.setAppElement('#root'); // Set the root element for accessibility

const AnnotationsUploadComponent = ({ onSubmit }) => {
    const [modalIsOpen, setIsOpen] = useState(false);
    const [infoModalIsOpen, setInfoModalIsOpen] = useState(false); // State for info modal
    const [jsonText, setJsonText] = useState(`{
  "name": "automobileManufacturingProcess",
  "frequencyPerMonth": 5,
  "tasks": [
    {
      "name": "Design Automobile",
      "duration": "120",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Design Software"}
      ]
    },
    {
      "name": "Approve Design",
      "duration": "30",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Approval Software", "timeUsed": "30", "unit": "minutes"}
      ]
    },
    {
      "name": "Procure Materials",
      "duration": "240",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Procurement System", "timeUsed": "240", "unit": "minutes"}
      ]
    },
    {
      "name": "Assemble Parts",
      "duration": "480",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Welder", "timeUsed": "480", "unit": "minutes"}
      ]
    },
    {
      "name": "Quality Check",
      "duration": "60",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Inspection Device"}
      ]
    },
    {
      "name": "Paint Automobile",
      "duration": "90",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Paint Sprayer", "timeUsed": "90", "unit": "minutes"}
      ]
    },
    {
      "name": "Final Inspection",
      "duration": "45",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Inspection Device"}
      ]
    },
    {
      "name": "Deliver Automobile",
      "duration": "180",
      "timeUnit": "minutes",
      "resourcesUsed": [
        {"resourceName": "Delivery Vehicle", "timeUsed": "180", "unit": "minutes"}
      ]
    }
  ],
  "resources": [
    {"name": "Design Software", "type": "atomic", "fuelPerUse": "30", "fuelType": "Energy_Romania", "fuelUnit": "kwh", "timeUnit": "hour"},
    {"name": "Approval Software", "type": "atomic", "fuelPerUse": "4", "fuelType": "Energy_Romania", "fuelUnit": "kwh", "timeUnit": "hour"},
    {"name": "Procurement System", "type": "atomic", "fuelPerUse": "8", "fuelType": "Energy_Romania", "fuelUnit": "kwh", "timeUnit": "hour"},
    {"name": "Welder", "type": "atomic", "fuelPerUse": "5", "fuelType": "Diesel", "fuelUnit": "l", "timeUnit": "hour"},
    {"name": "Inspection Device", "type": "atomic", "fuelPerUse": "100", "fuelType": "Energy_Romania", "fuelUnit": "kwh", "timeUnit": "hour"},
    {"name": "Paint Sprayer", "type": "atomic", "fuelPerUse": "3", "fuelType": "Diesel", "fuelUnit": "l", "timeUnit": "hour"},
    {"name": "Delivery Vehicle", "type": "atomic", "fuelPerUse": "10", "fuelType": "Diesel", "fuelUnit": "l", "timeUnit": "hour"}
  ]
}`);

    const openModal = () => setIsOpen(true);
    const closeModal = () => setIsOpen(false);

    const openInfoModal = () => setInfoModalIsOpen(true); // Open info modal
    const closeInfoModal = () => setInfoModalIsOpen(false); // Close info modal

    const handleFileUpload = (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            setJsonText(reader.result);
        }
        reader.readAsText(file);
    };

    const handleSubmit = () => {
        onSubmit(jsonText);
        closeModal();
    };

    const jsonKeyExplanations = {
        "name": "The name of the process.",
        "frequencyPerMonth": "How often this process occurs in a month",
        "tasks": "The list of tasks involved in the process.",
        "task.name": "The name of the task",
        "task.duration": "The duration of the task.",
        "task.timeUnit": "The unit of time for the duration (e.g., minutes, hours).",
        "task.resourcesUsed": "Resources used for the task.",
        "task.resourcesUsed.resourceName": "The name of the resource.",
        "task.resourcesUsed.timeUsed": "The time used by the resource.",
        "task.resourcesUsed.unit": "The unit of time used by the resource.",
        "resources": "List of resources for the process.",
        "resources.type": "The type of resource - only atomic",
        "resources.fuelPerUse": "The amount of fuel used per use.",
        "resources.fuelType": "The type of fuel used - from the emission factor list.",
        "resources.fuelUnit": "The abbreviated unit of the fuel used.",
    };

    return (

        <div className="file-upload-component">
            <button className="upload-button" onClick={openModal}>Upload File Annotations</button>
            <Modal className="modal" isOpen={modalIsOpen} onRequestClose={closeModal}>
                <div className="modal-header">
                    <h2 className="modal-title">Upload Annotations or Write Here</h2>
                    <div className="info" onClick={openInfoModal}>
                        <i className="fas fa-info-circle"></i>
                    </div>
                </div>
                <AceEditor
                    mode="json"
                    theme="github"
                    value={jsonText}
                    onChange={(value) => setJsonText(value)}
                    name="json-editor"
                    editorProps={{ $blockScrolling: true }}
                    width="100%"
                    height="400px"
                    wrapEnabled={true}
                    setOptions={{
                        useWorker: false, // Disable worker to avoid a warning in console
                    }}
                />
                <br />
                <input className="file-input" type="file" onChange={handleFileUpload} />
                <br />
                <button className="submit-button" onClick={handleSubmit}>Submit</button>
                <button className="close-button" onClick={closeModal}>Close</button>
            </Modal>
            <Modal className="info-modal" isOpen={infoModalIsOpen} onRequestClose={closeInfoModal}>
                <div className="info-modal-content">
                    <h2 className="modal-title">JSON Key Explanations</h2>
                    <ul>
                        {Object.keys(jsonKeyExplanations).map(key => (
                            <li key={key}><strong>{key}</strong>: {jsonKeyExplanations[key]}</li>
                        ))}
                    </ul>
                    <button className="close-button" onClick={closeInfoModal}>Close</button>
                </div>
            </Modal>
        </div>
    );
};

export default AnnotationsUploadComponent;
