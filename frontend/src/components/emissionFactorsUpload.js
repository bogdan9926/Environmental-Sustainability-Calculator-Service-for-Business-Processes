import React, { useState } from 'react';
import Modal from 'react-modal';
import AceEditor from 'react-ace';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/mode-javascript';
import 'ace-builds/src-noconflict/theme-github';
import './fileUpload.css';

Modal.setAppElement('#root'); // Set the root element for accessibility

const EmissionFactorsUploadComponent = ({ onSubmit }) => {
    const [modalIsOpen, setIsOpen] = useState(false);
    const [infoModalIsOpen, setInfoModalIsOpen] = useState(false); // State for info modal
    const [jsonText, setJsonText] = useState(`[
  {
    "fuelType": "Diesel",
    "unit": "l",
    "factor": 2.70553
  },
  {
    "fuelType": "Petrol",
    "unit": "l",
    "factor": 2.32055
  },
  {
    "fuelType": "Natural Gas",
    "unit": "m3",
    "factor": 1.89876
  },
  {
    "fuelType": "Electricity",
    "unit": "kwh",
    "factor": 0.23314
  }
]
`);

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
        "fuelType": "The name of the fuel (e.g Diesel)",
        "unit": "The abbreviated unit of measurement for the fuel (e.g. l, m3).",
        "factor": "The emission factor for the fuel type."
    };

    return (
        <div className="file-upload-component">
            <button className="upload-button" onClick={openModal}>Upload Fuel Emissions</button>
            <Modal className="modal" isOpen={modalIsOpen} onRequestClose={closeModal}>
                <div className="modal-header">
                    <h2 className="modal-title">Upload Fuel Emissions or Write Here</h2>
                    <div className="info" onClick={openInfoModal}>
                        <i className="fas fa-info-circle"></i>
                    </div>
                    {/* Info button */}
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

export default EmissionFactorsUploadComponent;
