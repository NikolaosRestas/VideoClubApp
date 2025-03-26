import React, { useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Alert, Button } from "@mui/material";
import EditVideoclubModal from "./EditVideoclubModal";

export default function VideoclubsTableComponent({ videoclubs, onChange }) {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedVideoclub, setSelectedVideoclub] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const handleEditModalOpen = (videoclub) => {
        setSelectedVideoclub(videoclub);
        setIsEditModalOpen(true);
    };

    const handleDelete = (videoclub) => {
        fetch(`/videoClubs/delete/${videoclub.id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        })
            .then(response => {
                if (response.ok) {
                    setIsSuccessfulDelete({
                        videoclubName: videoclub.name,
                        videoclubPhone: videoclub.phone
                    });

                    setTimeout(() => setIsSuccessfulDelete(null), 5000);
                    onChange(videoclubs.filter(c => c.id !== videoclub.id));
                } else {
                    throw new Error("Failed to delete the videoclubs.");
                }
            })
            .catch(error => {
                setErrorMessage(`Error: Could not delete ${videoclub.name} because it a key to other entities. Try and delete those first.`);
                setTimeout(() => setErrorMessage(null), 5000);
            });
    };

    const handleEditModalClose = () => {
        setIsEditModalOpen(false);
    };

    return (
        <>
            <TableContainer component={Paper} className="shadow-lg rounded-lg">
                <Table className="min-w-max" aria-label="videoclub table">
                    <TableHead>
                        <TableRow>
                            <TableCell className="font-bold">Id</TableCell>
                            <TableCell align="right" className="font-bold">Name</TableCell>
                            <TableCell align="right" className="font-bold">Phone</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {videoclubs.map((videoclub) => (
                            <TableRow key={videoclub.id} className="hover:bg-gray-100">
                                <TableCell className="py-3">{videoclub.id}</TableCell>
                                <TableCell align="right" className="py-3">{videoclub.name}</TableCell>
                                <TableCell align="right" className="py-3">{videoclub.phone}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => handleEditModalOpen(videoclub)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => handleDelete(videoclub)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedVideoclub && (
                <EditVideoclubModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedVideoclub}
                />
            )}

            {/* Success Message */}
            {isSuccessfulDelete && (
                <div className="relative h-32 flex justify-center">
                    <Alert severity="success">
                        The videoclub <strong>{isSuccessfulDelete.videoclubName}</strong> was deleted successfully!
                    </Alert>
                </div>
            )}

            {/* Error Message */}
            {errorMessage && (
                <div className="relative h-32 flex justify-center">
                    <Alert severity="error">{errorMessage}</Alert>
                </div>
            )}
        </>
    );
}