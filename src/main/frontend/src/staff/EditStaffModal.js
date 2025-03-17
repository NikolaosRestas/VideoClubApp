import React, {useEffect, useState} from 'react';
import {Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Alert,Select,MenuItem} from '@mui/material';

export default function EditStaffModal({isOpen, onClose, clientData, onSave}) {
    const [editedData, setEditedData] = useState({...clientData});
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [videoClubsData, setVideoClubsData] = useState ([]);

    useEffect(() => {
        fetch('/videoClubs')
            .then(response => response.json())
            .then(data => {
                setVideoClubsData(data);
            })
            .catch(error => {
                console.error('Error fetching the videoClubs:', error);
            });
    }, []);

    useEffect(() => {
        setEditedData({...clientData}); // Update local state when the clientData prop changes
    }, [clientData]);

    const handleSave = () => {
        clientData.name = editedData.name;
        clientData.phone = editedData.phone;
        clientData.videoClub = editedData.videoClub;
        clientData.videoClubId = editedData.videoClubId;

        fetch(`/staff/update/${clientData.id}`,
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

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
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
                ['videoClub']: videoClub,
            }));
        }
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>Edit Customer</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Name"
                        name="name"
                        value={editedData.name}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Phone"
                        name="phone"
                        value={editedData.phone}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="videoClub"
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
                    {isSuccessAlertOpen && <Alert severity="success">The Staff update was successful!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
};