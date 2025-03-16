import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

export default function NewPsGameModal({isOpen,onClose,onSave}){
    const [psgame,setpsgame] = useState({title:""});
    const [isSuccessAlertOpen,setIsSuccessAlertOpen] = useState(false);
    const [videoclubsData,setVideoclubsData] = useState([]);
    const [customersData,setCustomersData] = useState([]);


    useEffect(() => {
        Promise.all([
            fetch('/videoClubs').then(response => response.json()),
            fetch('/customers').then(response => response.json())
        ])
        .then(([videoClubsData, customersData]) => {
            setVideoclubsData(videoClubsData);
            setCustomersData(customersData);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
    }, []);

    useEffect(() => {
        setpsgame({...psgame}); // Update local state when the clientData prop changes
    }, [psgame]);

    const handleSave = () => {
        fetch(`/PsGames/add`,
            {
                method: 'POST',
                body: JSON.stringify(psgame),
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then((data) => {
                onSave(data);
                setIsSuccessAlertOpen(true);
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Error while calling the API:', error);
            });

        console.log("Save changes:", psgame);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setpsgame((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New PsGame</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Title"
                        name="title"
                        value={psgame.title}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Console"
                        name="console"
                        value={psgame.console}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Company"
                        name="company"
                        value={psgame.company}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="VideoClub"
                        name="videoClubId"
                        value={psgame.videoClubId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Videoclub"
                        name="videoClubId"
                        value={psgame.videoClubId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="VideoClub Empty" disabled>Select a Videoclub </MenuItem>
                        {
                            videoclubsData.map((videoclub) => (
                                <MenuItem key={videoclub.id} value={videoclub.id}> {videoclub.name} </MenuItem>))
                        }
                    </Select>
                    <TextField
                        label="Customer"
                        name="customerId"
                        value={psgame.customerId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Customer"
                        name="customerId"
                        value={psgame.customerId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="Customer Empty" disabled>Select a Customer </MenuItem>
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
                    {isSuccessAlertOpen && <Alert severity="success">The psGame added successfully!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}