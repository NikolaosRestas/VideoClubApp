import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

export default function NewPsGameModal({ isOpen, onClose, onSave }) {
    const [psgame, setpsgame] = useState({ title: "" });
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
        fetch(`/PsGames/add`, {
            method: "POST",
            body: JSON.stringify(psgame),
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                onSave(data);
                setIsSuccessAlertOpen(true);

                // Delay modal close to allow alert visibility
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                    onClose();
                }, 3000);
            })
            .catch(error => {
                console.error("Error while calling the API:", error);
            });

        console.log("Save changes:", psgame);
    };

    const handleCancel = () => {
        onClose();
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setpsgame(prevData => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New PsGame</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Title"
                        name="title"
                        value={psgame.title}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Console"
                        name="console"
                        value={psgame.console || ""}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Company"
                        name="company"
                        value={psgame.company || ""}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        displayEmpty
                        name="videoClubId"
                        value={psgame.videoClubId || ""}
                        onChange={handleInputChange}
                        fullWidth
                    >
                        <MenuItem value="" disabled>Select a Videoclub</MenuItem>
                        {videoclubsData.map(videoclub => (
                            <MenuItem key={videoclub.id} value={videoclub.id}>
                                {videoclub.name}
                            </MenuItem>
                        ))}
                    </Select>
                    <Select
                        displayEmpty
                        name="customerId"
                        value={psgame.customerId || ""}
                        onChange={handleInputChange}
                        fullWidth
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
                <div className="fixed bottom-4 right-4 z-50">
                    <Alert severity="success">The PS Game was added successfully!</Alert>
                </div>
            )}
            </Dialog>
        </>
    );
}