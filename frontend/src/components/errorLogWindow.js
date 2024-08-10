import React from 'react';
import './errorLogWindow.css';

const ErrorLogWindow = ({ errors }) => {
    return (
        <div className="error-log-window">
            {errors.length === 0 ? (
                <p>No errors to display</p>
            ) : (
                <ul>
                    {errors.map((error, index) => (
                        <li key={index}>{error}</li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ErrorLogWindow;
