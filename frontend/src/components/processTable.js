import React from 'react';
import './processTable.css';
import { format, parseISO, isValid } from 'date-fns';

const ProcessTable = ({ tasks }) => {
    return (
        <table className="process-table">
            <thead>
            <tr>
                <th>Task Name</th>
                <th>CO2 Emission Value</th>
                <th>Start Time</th>
                <th>End Time</th>
            </tr>
            </thead>
            <tbody>
            {tasks.map((task, index) => (
                <tr key={index}>
                    <td>{task.taskName}</td>
                    <td>{task.co2EmissionValue} kg</td>
                    <td>{task.startTime ? (isValid(parseISO(task.startTime)) ? format(parseISO(task.startTime), 'PPpp') : 'Invalid date') : 'No date provided'}</td>
                    <td>{task.endTime ? (isValid(parseISO(task.endTime)) ? format(parseISO(task.endTime), 'PPpp') : 'Invalid date') : 'No date provided'}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default ProcessTable;
