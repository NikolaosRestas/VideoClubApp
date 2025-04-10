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

//export default function CdsTableComponent({cds,onChange}){
//    const [isEditModalOpen, setIsEditModalOpen] = useState({});
//    const [selectedClient, setSelectedClient] = useState(null);
//    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);
//
//    function onEdit(cd){
//        console.log('Edit Cd:',cd);
//        setSelectedClient(cd);
//        setIsEditModalOpen(true);
//    }
//
//    function onDelete (cd){
//           fetch(`/cd/delete/${cd.id}`, {
//               method: 'DELETE',
//               headers: { 'Content-Type': 'application/json' },
//           })
//               .then(response => {
//                   if (response.ok) {
//                       setTimeout(() => {
//                           setIsSuccessfulDelete(false);
//                       }, 5000);
//                       onChange(cds.filter(c => c.id !== cd.id));
//                   }
//               });
//       };
//
//      const handleEditModalClose=()=>{
//           setIsEditModalOpen(false);
//      }
//
//    return (
//        <React.Fragment>
//            <TableContainer component={Paper} className="shadow-lg rounded-lg">
//                <Table className="min-w-max" aria-label="simple table">
//                    <TableHead>
//                        <TableRow>
//                            <TableCell className="font-bold">Id</TableCell>
//                            <TableCell align="right" className="font-bold">Name</TableCell>
//                            <TableCell align="right" className="font-bold">Artist</TableCell>
//                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
//                            <TableCell align="right" className="font-bold">Customer</TableCell>
//                            <TableCell align="right" className="font-bold">Actions</TableCell>
//                        </TableRow>
//                    </TableHead>
//                    <TableBody>
//                        {cds.map((cd) => (
//                            <TableRow
//                                key={cd.id}
//                                className="hover:bg-gray-100"
//                            >
//                                <TableCell component="th" scope="row" className="py-3">
//                                    {cd.id}
//                                </TableCell>
//                                <TableCell align="right" className="py-3">{cd.name}</TableCell>
//                                <TableCell align="right" className="py-3">{cd.artist}</TableCell>
//                                <TableCell align="right" className="py-3">{cd.videoClub.name}</TableCell>
//                                <TableCell align="right" className="py-3">{cd.customer.name}</TableCell>
//                                <TableCell align="right" className="py-3">
//                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => onEdit(cd)}>
//                                        Edit
//                                    </Button>
//                                    <span className="inline-block w-4"></span> {/* This creates space */}
//                                    <Button variant="contained" color="primary" onClick={() => onDelete(cd,cds)}>
//                                        Delete
//                                    </Button>
//                                </TableCell>
//                            </TableRow>
//                        ))}
//                    </TableBody>
//                </Table>
//            </TableContainer>
//
//            {selectedClient && (
//                <EditCdModal
//                    isOpen={isEditModalOpen}
//                    onClose={handleEditModalClose}
//                    clientData={selectedClient}
//                    onSave={onChange}
//                />
//            )}
//
//            {isSuccessfulDelete && (
//                <div className="relative h-32 flex flex-nowrap">
//                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
//                        <Alert severity="success">
//                            The cd {isSuccessfulDelete.cdName} was deleted successfully!
//                        </Alert>
//                    </div>
//                </div>
//            )}
//        </React.Fragment>
//    );
//}

export default function CdsTableComponent({ cds, onChange }) {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    function onEdit(cd) {
        console.log("Edit Cd:", cd);
        setSelectedClient(cd);
        setIsEditModalOpen(true);
    }

    function onDelete(cd) {
        fetch(`/cd/delete/${cd.id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        })
            .then(response => {
                if (response.ok) {
                    setIsSuccessfulDelete({
                        cdName: cd.name,
                        artist: cd.artist
                    });

                    setTimeout(() => setIsSuccessfulDelete(null), 5000);
                    onChange(cds.filter(c => c.id !== cd.id));
                } else {
                    throw new Error("Failed to delete the CD.");
                }
            })
            .catch(error => {
                setErrorMessage(`Error: Could not delete ${cd.name}`);
                setTimeout(() => setErrorMessage(null), 5000);
            });
    }

    const handleEditModalClose = () => {
        setIsEditModalOpen(false);
    };

    return (
        <>
            <TableContainer component={Paper} className="shadow-lg rounded-lg">
                <Table className="min-w-max" aria-label="cds table">
                    <TableHead>
                        <TableRow>
                            <TableCell className="font-bold">Id</TableCell>
                            <TableCell align="right" className="font-bold">Name</TableCell>
                            <TableCell align="right" className="font-bold">Artist</TableCell>
                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
                            <TableCell align="right" className="font-bold">Customer</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {cds.map((cd) => (
                            <TableRow key={cd.id} className="hover:bg-gray-100">
                                <TableCell className="py-3">{cd.id}</TableCell>
                                <TableCell align="right" className="py-3">{cd.name}</TableCell>
                                <TableCell align="right" className="py-3">{cd.artist}</TableCell>
                                <TableCell align="right" className="py-3">{cd.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">{cd.customer.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => onEdit(cd)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => onDelete(cd)}>
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
                    onSave={onChange}
                />
            )}

            {/* Success Message */}
            {isSuccessfulDelete && (
                <div className="relative h-32 flex justify-center">
                    <Alert severity="success">
                        The CD <strong>{isSuccessfulDelete.cdName}</strong> by {isSuccessfulDelete.artist} was deleted successfully!
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