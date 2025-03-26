import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

const NewCustomerModal = ({ isOpen, onClose, onSave }) => {
    const [customer, setCustomer] = useState({ name: "", phone: "", email: "", videoClubId: "" });
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [videoclubsData, setVideoClubsData] = useState([]);

    useEffect(() => {
        fetch('/videoClubs')
            .then(response => response.json())
            .then(data => {
                setVideoClubsData(data);
            })
            .catch(error => {
                console.error('Error fetching videoclubs:', error);
            });
    }, []);

    const handleSave = () => {

        fetch(`/customers/add`, {
            method: 'POST',
            body: JSON.stringify(customer),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add customer');
            }
            return response.json();
        })
        .then((data) => {
            onSave(data);
            setIsSuccessAlertOpen(true); // Show success alert

            // Set timeout to hide the alert after 5 seconds
            setTimeout(() => {
                setIsSuccessAlertOpen(false);
            }, 2000);
        })
        .catch((error) => {
            console.error('Error while calling the API:', error);
            alert('An error occurred while saving the customer.');
        });
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setCustomer((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Customer</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Name"
                        name="name"
                        value={customer.name}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Phone"
                        name="phone"
                        value={customer.phone}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Email"
                        name="email"
                        value={customer.email}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Videoclub"
                        name="videoClubId"
                        value={customer.videoClubId || ""}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="" disabled>Select a Videoclub</MenuItem>
                        {videoclubsData.map((videoclub) => (
                            <MenuItem key={videoclub.id} value={videoclub.id}>{videoclub.name}</MenuItem>
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
                    <Alert severity="success">The customer was added successfully!</Alert>
                </div>
            )}
            </Dialog>
        </React.Fragment>
    );
};

export default NewCustomerModal;