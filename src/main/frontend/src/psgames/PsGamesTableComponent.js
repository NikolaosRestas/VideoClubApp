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

export default function PsGamesTableComponent({PsGames,onChange}){
    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

   function onEdit(PsGame){
        console.log('Edit PsGame:',PsGame);
        setSelectedClient(PsGame);
        setIsEditModalOpen(true);
   }

    const handleEditModalClose=()=>{
        setIsEditModalOpen(false);
    }

    function onDelete (PsGame){
           fetch(`/PsGames/delete/${PsGame.id}`, {
               method: 'DELETE',
               headers: { 'Content-Type': 'application/json' },
           })
               .then(response => {
                   if (response.ok) {
                       setTimeout(() => {
                           setIsSuccessfulDelete(false);
                       }, 5000);
                       onChange(PsGames.filter(c => c.id !== PsGame.id));
                   }
               });
       };

    return (
        <React.Fragment>
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
                            <TableRow
                                key={PsGame.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {PsGame.id}
                                </TableCell>
                                <TableCell align="right" className="py-3">{PsGame.title}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.console}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.company}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">{PsGame.customer.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => onEdit(PsGame)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => onDelete(PsGame,PsGames)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedClient && (
                <EditPsGameModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedClient}
                    onSave={onChange}
                />
            )}

            {isSuccessfulDelete && (
                <div className="relative h-32 flex flex-nowrap">
                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                        <Alert severity="success">
                            The psgame {isSuccessfulDelete.PsGameTitle} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
}