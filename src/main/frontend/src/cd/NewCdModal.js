import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

const NewCdModal=({isOpen,onClose,onSave})=>{

    const [cd,setCd] = useState({name:""});
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
        setCd({...cd}); // Update local state when the clientData prop changes
    }, [cd]);

    const handleSave = () => {
        fetch(`/cd/add`,
            {
                method: 'POST',
                body: JSON.stringify(cd),
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

        console.log("Save changes:", cd);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setCd((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Cd</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Name"
                        name="name"
                        value={cd.name}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Artist"
                        name="artist"
                        value={cd.artist}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="VideoClub"
                        name="videoClubId"
                        value={cd.videoClubId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Videoclub"
                        name="videoClubId"
                        value={cd.videoClubId}
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
                        value={cd.customerId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Customer"
                        name="customerId"
                        value={cd.customerId}
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
                    {isSuccessAlertOpen && <Alert severity="success">The cd added successfully!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}

export default NewCdModal;