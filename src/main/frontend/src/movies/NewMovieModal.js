import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

const NewMovieModal = ({ isOpen, onClose, onSave }) => {
    const [movie, setMovie] = useState({ title: "", year: "", videoClubId: "", customerId: "" });
    const [isSuccessAlertOpen, setIsSuccessfulAlertOpen] = useState(false);
    const [videoclubsData, setVideoClubsData] = useState([]);
    const [customersData, setCustomersData] = useState([]);

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

    const handleSave = () => {
        fetch(`/movies/add`, {
            method: 'POST',
            body: JSON.stringify(movie),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(response => response.json())
        .then((data) => {
            onSave(data);
            setIsSuccessfulAlertOpen(true);

            // Delay the closing of the modal to allow the alert to show
            setTimeout(() => {
                setIsSuccessfulAlertOpen(false);
                onClose(); // Close modal after showing alert
            }, 2000);
        })
        .catch((error) => {
            console.error('Error while calling the API:', error);
        });

        console.log("Save changes:", movie);
    };

    const handleCancel = () => {
        onClose();
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setMovie((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Movie</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Title"
                        name="title"
                        value={movie.title}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Year"
                        name="year"
                        value={movie.year}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Videoclub"
                        name="videoClubId"
                        value={movie.videoClubId || ""}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="" disabled>Select a Videoclub</MenuItem>
                        {videoclubsData.map((videoclub) => (
                            <MenuItem key={videoclub.id} value={videoclub.id}> {videoclub.name} </MenuItem>
                        ))}
                    </Select>
                    <Select
                        label="Customer"
                        name="customerId"
                        value={movie.customerId || ""}
                        onChange={handleInputChange}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="" disabled>Select a Customer</MenuItem>
                        {customersData.map((customer) => (
                            <MenuItem key={customer.id} value={customer.id}> {customer.name} </MenuItem>
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
                    <Alert severity="success">The Movie was added successfully!</Alert>
                </div>
            )}
            </Dialog>
        </React.Fragment>
    );
};

export default NewMovieModal;