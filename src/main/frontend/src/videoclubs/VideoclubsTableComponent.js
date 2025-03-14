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
    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedVideoclub, setSelectedVideoclub] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    const handleEditModalOpen = (videoclub) => {
        setSelectedVideoclub(videoclub);
        setIsEditModalOpen(true);
    };

   const handleDelete = (videoclub) => {
           fetch(`/videoClubs/delete/${videoclub.id}`, {
               method: 'DELETE',
               headers: { 'Content-Type': 'application/json' },
           })
               .then(response => {
                   if (response.ok) {
                       setIsSuccessfulDelete({ videoclubName: videoclub.name });
                       setIsSuccessfulDelete({ videoclubPhone: videoclub.phone});
                       setTimeout(() => {
                           setIsSuccessfulDelete(false);
                       }, 5000);
                       onChange(videoclubs.filter(c => c.id !== videoclub.id));
                   }
               });
       };

    const handleEditModalClose = () => {
        setIsEditModalOpen(false);
    };

    return (
        <React.Fragment>
            <TableContainer component={Paper} className="shadow-lg rounded-lg">
                <Table className="min-w-max" aria-label="simple table">
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
                            <TableRow
                                key={videoclub.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {videoclub.id}
                                </TableCell>
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

            {isSuccessfulDelete && (
                <div className="relative h-32 flex flex-nowrap">
                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                        <Alert severity="success">
                            The videoclub {isSuccessfulDelete.videoclubName} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
}