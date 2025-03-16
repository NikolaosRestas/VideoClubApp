import React, {useEffect, useState} from 'react';
import {Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField,MenuItem,Select} from '@mui/material';

const NewMovieModal=({isOpen,onClose,onSave})=>{

    const [movie,setmovie] = useState({title:""});
    const [isSuccessAlertOpen,setIsSuccessfulAlertOpen] = useState(false);
    const [videoclubsData,setVideoClubsData] = useState([]);
    const [customersData,setCustomersData] = useState([]);

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
        setmovie({...movie}); // Update local state when the clientData prop changes
    }, [movie]);

    const handleSave = () => {
        fetch(`/movies/add`,
            {
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
                setTimeout(() => {
                    setIsSuccessfulAlertOpen(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Error while calling the API:', error);
            });

        console.log("Save changes:", movie);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };


    const handleCancel=()=>{
        onClose();
    }

     const handleInputChange = (event) => {
           const {name, value} = event.target;
           setmovie((prevData) => ({
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
                        value={movie.title}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Year"
                        name="year"
                        value={movie.year}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="VideoClub"
                        name="videoClubId"
                        value={movie.videoClubId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Videoclub"
                        name="videoClubId"
                        value={movie.videoClubId}
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
                        value={movie.customerId}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Customer"
                        name="customerId"
                        value={movie.customerId}
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
                    {isSuccessAlertOpen && <Alert severity="success">The Movie added successfully!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
};

export default NewMovieModal;