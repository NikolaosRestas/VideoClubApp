import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {Alert,Button} from "@mui/material";
import {useState} from "react";
import EditPsGameModal from "./EditPsGameModal";

export default function PsGamesTableComponent({ PsGames, onChange }) {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(null);

    function onEdit(PsGame) {
        console.log("Edit PsGame:", PsGame);
        setSelectedClient(PsGame);
        setIsEditModalOpen(true);
    }

    const handleEditModalClose = () => {
        setIsEditModalOpen(false);
    };

    function onDelete(PsGame) {
        fetch(`/PsGames/delete/${PsGame.id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        })
            .then(response => {
                if (response.ok) {
                    setIsSuccessfulDelete(PsGame.title); // Store the deleted game's title
                    onChange(PsGames.filter(c => c.id !== PsGame.id));

                    // Remove the success message after 5 seconds
                    setTimeout(() => {
                        setIsSuccessfulDelete(null);
                    }, 5000);
                }
            })
            .catch(error => {
                console.error("Error deleting the PsGame:", error);
            });
    }

    return (
        <>
            <TableContainer component={Paper} className="shadow-lg rounded-lg">
                <Table className="min-w-max" aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell className="font-bold">Id</TableCell>
                            <TableCell align="right" className="font-bold">Title</TableCell>
                            <TableCell align="right" className="font-bold">Console</TableCell>
                            <TableCell align="right" className="font-bold">Company</TableCell>
                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
                            <TableCell align="right" className="font-bold">Customer</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {PsGames.map((PsGame) => (
                            <TableRow key={PsGame.id} className="hover:bg-gray-100">
                                <TableCell component="th" scope="row" className="py-3">{PsGame.id}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.title}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.console}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.company}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.customer.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => onEdit(PsGame)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* Adds spacing */}
                                    <Button variant="contained" color="primary" onClick={() => onDelete(PsGame)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {/* Edit Modal */}
            {selectedClient && (
                <EditPsGameModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedClient}
                    onSave={onChange}
                />
            )}

            {/* Success Alert */}
            {isSuccessfulDelete && (
                <div className="fixed bottom-4 right-4 z-50">
                    <Alert severity="success">
                        The PsGame "{isSuccessfulDelete}" was deleted successfully!
                    </Alert>
                </div>
            )}
        </>
    );
}