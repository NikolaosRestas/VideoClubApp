import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

const NewCdModal = ({ isOpen, onClose, onSave }) => {
    const [cd, setCd] = useState({ name: "" });
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [videoclubsData, setVideoclubsData] = useState([]);
    const [customersData, setCustomersData] = useState([]);

    useEffect(() => {
        Promise.all([
            fetch("/videoClubs").then(response => response.json()),
            fetch("/customers").then(response => response.json())
        ])
            .then(([videoClubsData, customersData]) => {
                setVideoclubsData(videoClubsData);
                setCustomersData(customersData);
            })
            .catch(error => {
                console.error("Error fetching data:", error);
            });
    }, []);

    const handleSave = () => {
        fetch("/cd/add", {
            method: "POST",
            body: JSON.stringify(cd),
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                onSave(data);
                setIsSuccessAlertOpen(true);

                // Delay modal close to allow the alert to be seen
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                    onClose();
                }, 5000);
            })
            .catch(error => {
                console.error("Error while calling the API:", error);
            });

        console.log("Save changes:", cd);
    };

    const handleCancel = () => {
        onClose();
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setCd(prevData => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Cd</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Name"
                        name="name"
                        value={cd.name}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Artist"
                        name="artist"
                        value={cd.artist}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Videoclub"
                        name="videoClubId"
                        value={cd.videoClubId || ""}
                        onChange={handleInputChange}
                        fullWidth
                        displayEmpty
                    >
                        <MenuItem value="" disabled>Select a Videoclub</MenuItem>
                        {videoclubsData.map(videoclub => (
                            <MenuItem key={videoclub.id} value={videoclub.id}>
                                {videoclub.name}
                            </MenuItem>
                        ))}
                    </Select>
                    <Select
                        label="Customer"
                        name="customerId"
                        value={cd.customerId || ""}
                        onChange={handleInputChange}
                        fullWidth
                        displayEmpty
                    >
                        <MenuItem value="" disabled>Select a Customer</MenuItem>
                        {customersData.map(customer => (
                            <MenuItem key={customer.id} value={customer.id}>
                                {customer.name}
                            </MenuItem>
                        ))}
                    </Select>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCancel}>Cancel</Button>
                    <Button onClick={handleSave} color="primary">
                        Save
                    </Button>
                </DialogActions>
                 {isSuccessAlertOpen && (
                <div className="fixed bottom-4 right-4">
                    <Alert severity="success">The CD was added successfully!</Alert>
                </div>
                )}
            </Dialog>
        </>
    );
};

export default NewCdModal;