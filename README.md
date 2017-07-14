# smart_factory
# Author: Manisha Savale.
Smart Factory - IoT application based on LWM2M(light-weight machine to machine) protocol using REST.

Smart Factory is an hypothetical factory having a production line where robots perform all the activities. 
There are 4 robots: manufuctering robot, assembly robot, QA robot and packaging robot. These are controlled and observed by the workstation. The workstation and the robot clients communicate based on LWM2M protocol.

Functional Overview: The clients are registered at the workstation as per the protocol. Each client keeps sending its rate of workdone to the workstation e.g. manufacturing robot sends the manufactering rate. When the QA robot's defect rate exceeds the predefined threshold then the production line is halted by the workstation. I have used MongoDB as a database.
