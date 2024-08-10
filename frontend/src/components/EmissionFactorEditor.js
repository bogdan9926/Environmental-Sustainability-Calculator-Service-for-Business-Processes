import React, { useState } from 'react';
import Modal from 'react-modal';
import './emissionFactorEditor.css';

Modal.setAppElement('#root');

const EmissionFactorEditor = ({ emissionFactors, setEmissionFactors, onSubmit }) => {
    const [selectedEmissionFactor, setSelectedEmissionFactor] = useState(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setSelectedEmissionFactor(null);
        setModalIsOpen(false);
    };

    const handleEmissionFactorSubmit = async () => {
        if (selectedEmissionFactor !== null) {
            const updatedEmissionFactor = emissionFactors[selectedEmissionFactor];
            await onSubmit(updatedEmissionFactor);
            closeModal();
        }
    };

    const handleEmissionFactorChange = (e) => {
        const { name, value } = e.target;
        if (selectedEmissionFactor !== null) {
            const updatedEmissionFactors = [...emissionFactors];
            updatedEmissionFactors[selectedEmissionFactor][name] = value;
            setEmissionFactors(updatedEmissionFactors);
        }
    };

    const handleSelectorChange = (e) => {
        setSelectedEmissionFactor(parseInt(e.target.value, 10));
    };

    return (
        <div className="emission-factor-editor">
            <button onClick={openModal} className="edit-emission-factors-btn">Edit Emission Factors</button>
            <Modal
                className="modal"
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Edit Emission Factor"
            >
                <h3>Edit Emission Factors</h3>
                <div className="input-group">
                    <label>Fuel Type:</label>
                    <select onChange={handleSelectorChange} value={selectedEmissionFactor ?? ''}>
                        <option value="" disabled>Select an Emission Factor</option>
                        {emissionFactors.map((emission, index) => (
                            <option key={index} value={index}>
                                {emission.fuelType}
                            </option>
                        ))}
                    </select>
                </div>
                {selectedEmissionFactor !== null && (
                    <>
                        <div className="input-group">
                            <label>Unit:</label>
                            <input
                                type="text"
                                name="unit"
                                value={emissionFactors[selectedEmissionFactor].unit}
                                onChange={handleEmissionFactorChange}
                            />
                        </div>
                        <div className="input-group">
                            <label>Factor:</label>
                            <input
                                type="number"
                                name="factor"
                                value={emissionFactors[selectedEmissionFactor].factor}
                                onChange={handleEmissionFactorChange}
                            />
                        </div>
                        <button className="submit-button" onClick={handleEmissionFactorSubmit}>Submit</button>
                    </>
                )}
                <button className="close-button" onClick={closeModal}>Close</button>
            </Modal>
        </div>
    );
};

export default EmissionFactorEditor;
