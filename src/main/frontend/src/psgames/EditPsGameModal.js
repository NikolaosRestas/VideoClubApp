import React, {useEffect, useState} from 'react';
import {Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Alert,Select,MenuItem} from '@mui/material';

export default function EditPsGameModal({isOpen,onClose,clientData,onSave}){

     const [editedData,setEditedData] = useState({...clientData});
     const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
     const [videoClubsData, setVideoClubsData] = useState ([]);
     const [customersData, setCustomersData] = useState ([]);

     useEffect(() => {
        Promise.all([
            fetch('/videoClubs').then(response => response.json()),
            fetch('/customers').then(response => response.json())
        ])
        .then(([videoClubsData, customersData]) => {
            setVideoClubsData(videoClubsData);
            setCustomersData(customersData);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
    }, []);

    useEffect(() => {
        setEditedData({...clientData}); // Update local state when the clientData prop changes
    }, [clientData]);

    const handleCancel=()=>{
        onClose();
    }

    const handleSave = () => {
        clientData.title = editedData.title;
        clientData.console = editedData.console;
        clientData.company = editedData.company;
        clientData.videoClub = editedData.videoClub;
        clientData.videoClubId = editedData.videoClubId;
        clientData.customer = editedData.customer;
        clientData.customerId = editedData.customerId;

        fetch(`/PsGames/update/${clientData.id}`,
            {
                method: 'PUT',
                body: JSON.stringify(clientData),
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then((data) => {
                setIsSuccessAlertOpen(true);
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Error while calling the API:', error);
            });

        console.log("Save changes:", clientData);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setEditedData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
        if (name === 'videoClubId') {
            let videoClub = videoClubsData.find(c => c.id === value);
            console.log('videoClub: ', videoClub);
            setEditedData((prevData) => ({
                ...prevData,
                // eslint-disable-next-line
                videoClub: videoClub,
            }));
        }

        if (name === 'customerId') {
            let customer = customersData.find(c => c.id === value);
            console.log('customer: ', customer);
            setEditedData((prevData) => ({
                ...prevData,
                // eslint-disable-next-line
                customer: customer,
            }));
        }

    };

     return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>Edit Customer</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Title"
                        name="title"
                        value={editedData.title}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Console"
                        name="console"
                        value={editedData.console}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Company"
                        name="company"
                        value={editedData.company}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="VideoClub"
                        name="videoClubId"
                        value={editedData.videoClubId || editedData.videoClub.id}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        {
                            videoClubsData.map((videoclub) => (
                                <MenuItem key={videoclub.id} value={videoclub.id}> {videoclub.name} </MenuItem>))
                        }
                    </Select>
                    <Select
                        label="Customer"
                        name="customerId"
                        value={editedData.customerId || editedData.customer.id}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        {
                            customersData.map((customer) => (
                                <MenuItem key={customer.id} value={customer.id}> {customer.name} </MenuItem>))
                        }
                    </Select>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCancel}>Cancel</Button>
                    <Button onClick={handleSave} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>

            <div className="relative h-32 flex flex-nowrap">
                <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                    {isSuccessAlertOpen && <Alert severity="success">The PsGame update was successful!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}