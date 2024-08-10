# Sustainability Calculator for Business Process Management (BPM)

### Overview 
This repository contains the implementation of a Sustainability Calculator designed to integrate with Business Process Management (BPM) systems. The calculator focuses on accurately calculating and monitoring CO2 emissions across various business processes, supporting sustainability initiatives within organizations.

The project is part of a thesis that explores the integration of sustainability into BPM, highlighting the need for tools that manage and assess the environmental footprint of business operations effectively.

### Features
 - CO2 Emission Calculation: The calculator estimates CO2 emissions using emission factors that can be customized by the user to match specific business requirements. The calculations ensure consistency and accuracy across various activities and resources.

 - Real-Time Emission Tracking: The system processes data in real-time, providing immediate feedback on emission levels during business process execution. This feature is essential for ongoing monitoring and timely decision-making.

 - Modular Architecture: The system's architecture supports scalability and adaptability. It is designed using containers and decoupled components, allowing for easy extension and horizontal/vertical scaling.

 - Integration with BPM Engines: The sustainability calculator currently integrates with Camunda 7 and JBPM, two widely used BPM engines. The system is designed to be versatile, supporting potential integration with additional BPM engines.

 - User-Friendly Dashboard: Developed using React, the dashboard allows users to upload, edit, and visualize annotations and emission factors. It provides an intuitive interface for managing sustainability data and accessing real-time emission reports.

### Requirements
#### Functional Requirements
 - Integration with BPM Engines: Support for Camunda 7 and JBPM to facilitate communication and real-time monitoring.
 - Real-Time Data Processing: Use of Stomp WebSockets for real-time data communication.
 - Data Management: Reliable storage and management of process details, annotations, and emission factors using PostgreSQL.
 - Emission Calculation: Accurate CO2 emission calculations using predefined emission factors.
#### Non-Functional Requirements
 - Scalability: The system is designed to handle increased loads and integrate new features easily.
 - Usability: The dashboard provides an intuitive interface, enhancing user interaction and accessibility.
 - Maintainability: The modular architecture ensures ease of maintenance and extensibility.
 - Portability: Use of Docker for deployment across various operating systems.
 - Reliability and Data Integrity: Validation and error handling mechanisms ensure accurate and consistent data for sustainability calculations.

### Limitations
 - Data Precision: The accuracy of emission calculations depends on the precision of the input data. Incomplete or inaccurate data may lead to unreliable results.
 - Limited Resource Types: Currently, only atomic resources are considered in the emission calculations. Shared resources are not yet supported.
 - BPM Engine Compatibility: The system supports only Camunda 7 and JBPM. Other BPM engines are not yet integrated.
 - Scalability Challenges: Handling large volumes of data and extensive business processes may require additional computational resources to maintain performance.

### Future Work
 - Shared Resource Integration: Introduce shared resources in emission calculations for a more comprehensive assessment.
 - Extended BPM Engine Support: Add compatibility with other BPM engines, including Camunda 8 and IBM BPM.
 - Enhanced Use Cases: Enable process execution from the dashboard for testing configurations and real-time process flow manipulation based on emission levels. 
 - Extended Emission Types: Include other greenhouse gas (GHG) emissions, such as methane and nitrous oxide, in the sustainability calculations.

### Setup
 - Clone the project
 - Open the project in your chosen IDE compatible with Spring
 - Build all Gradle dependencies
 - In order to run in a containerized environment, make sure Docker is running, before running the *docker-build.sh script*. The script has 1 argument, representing the docker compose profile wanted. There are 2 profiles: camunda and jbpm.
 - If the script ran without any issues the frontend dashboard located at *localhost:3000* should look like this:
![](C:\Users\bogda\Downloads\ss-frontend-empty.png)