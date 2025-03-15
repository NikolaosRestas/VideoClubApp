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
import EditCdModal from "./EditCdModal";

export default function CdsTableComponent({cds,onChange}){
    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    const handleEditModalOpen=(cd)=>{
        setSelectedClient(cd);
        setIsEditModalOpen(true);
    }

    const handleEditModalClose=()=>{
        setIsEditModalOpen(false);
    }

    const handleDelete = (cd) => {
           fetch(`/cd/delete/${cd.id}`, {
               method: 'DELETE',
               headers: { 'Content-Type': 'application/json' },
           })
               .then(response => {
                   if (response.ok) {
                       setIsSuccessfulDelete({ cdName: cd.name });
                       setIsSuccessfulDelete({ cdArtist: cd.artist});
                       setTimeout(() => {
                           setIsSuccessfulDelete(false);
                       }, 5000);
                       onChange(cds.filter(c => c.id !== cd.id));
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
                            <TableCell align="right" className="font-bold">Name</TableCell>
                            <TableCell align="right" className="font-bold">Artist</TableCell>
                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
                            <TableCell align="right" className="font-bold">Customer</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {cds.map((cd) => (
                            <TableRow
                                key={cd.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {cd.id}
                                </TableCell>
                                <TableCell align="right" className="py-3">{cd.name}</TableCell>
                                <TableCell align="right" className="py-3">{cd.artist}</TableCell>
                                <TableCell align="right" className="py-3">{cd.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">{cd.customer.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => handleEditModalOpen(cd)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => handleDelete(cd)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedClient && (
                <EditCdModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedClient}
                />
            )}

            {isSuccessfulDelete && (
                <div className="relative h-32 flex flex-nowrap">
                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                        <Alert severity="success">
                            The cd {isSuccessfulDelete.cdName} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
}