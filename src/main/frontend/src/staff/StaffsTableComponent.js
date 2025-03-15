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
import EditStaffModal from "./EditStaffModal";


export default function StaffsTableComponent({ staffs, onChange }) {
    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    const handleEditModalOpen = (staff) => {
        setSelectedClient(staff);
        setIsEditModalOpen(true);
    };

   const handleDelete = (staff) => {
           fetch(`/staff/delete/${staff.id}`, {
               method: 'DELETE',
               headers: { 'Content-Type': 'application/json' },
           })
               .then(response => {
                   if (response.ok) {
                       setIsSuccessfulDelete({ staffName: staff.name });
                       setIsSuccessfulDelete({ staffPhone: staff.phone});
                       setTimeout(() => {
                           setIsSuccessfulDelete(false);
                       }, 5000);
                       onChange(staffs.filter(c => c.id !== staff.id));
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
                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {staffs.map((staff) => (
                            <TableRow
                                key={staff.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {staff.id}
                                </TableCell>
                                <TableCell align="right" className="py-3">{staff.name}</TableCell>
                                <TableCell align="right" className="py-3">{staff.phone}</TableCell>
                                <TableCell align="right" className="py-3">{staff.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => handleEditModalOpen(staff)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => handleDelete(staff)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedClient && (
                <EditStaffModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedClient}
                />
            )}

            {isSuccessfulDelete && (
                <div className="relative h-32 flex flex-nowrap">
                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                        <Alert severity="success">
                            The staff {isSuccessfulDelete.staffName} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
}