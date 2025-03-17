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
import EditCustomerModal from "./EditCustomerModal";


export default function CustomersTableComponent({ customers, onChange }) {
    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    function onEdit(customer){
        console.log('Edit Customer', customer);
        setSelectedClient(customer);
        setIsEditModalOpen(true);
    }

   function onDelete(customer){
           fetch(`/customers/delete/${customer.id}`, {
               method: 'DELETE',
               headers: { 'Content-Type': 'application/json' },
           })
               .then(response => {
                   if (response.ok) {
                       setIsSuccessfulDelete({ customerName: customer.name });
                       setIsSuccessfulDelete({ customerPhone: customer.phone});
                       setIsSuccessfulDelete({ customerEmail: customer.email});
                       setTimeout(() => {
                           setIsSuccessfulDelete(false);
                       }, 5000);
                       onChange(customers.filter(c => c.id !== customer.id));
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
                            <TableCell align="right" className="font-bold">Email</TableCell>
                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {customers.map((customer) => (
                            <TableRow
                                key={customer.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {customer.id}
                                </TableCell>
                                <TableCell align="right" className="py-3">{customer.name}</TableCell>
                                <TableCell align="right" className="py-3">{customer.phone}</TableCell>
                                <TableCell align="right" className="py-3">{customer.email}</TableCell>
                                <TableCell align="right" className="py-3">{customer.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => onEdit(customer)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => onDelete(customer)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedClient && (
                <EditCustomerModal
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
                            The customer {isSuccessfulDelete.customerName} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
}