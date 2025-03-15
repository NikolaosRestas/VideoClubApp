import React, {useEffect, useState} from 'react';
import CustomersTableComponent from "./CustomersTableComponent";
import {Button} from "@mui/material";
import NewCustomerModal from "./NewCustomerModal";
import Loader from "../loader/loader";

const CustomersPage = () => {
    const [customersData, setCustomersData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isNewCustomerModalOpen, setNewCustomerModalOpen] = useState(false);

    useEffect(() => {
        fetch('/customers')
            .then(response => response.json())
            .then(data => {
                setCustomersData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching customers:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newCustomer = () => {
        setNewCustomerModalOpen(true);
    };

    const handleCloseNewCustomerModal = () => {
        setNewCustomerModalOpen(false);
    };

    const handleSaveNewCustomer = customer => {
        setCustomersData(prevCustomers => [...prevCustomers, customer]);
        handleCloseNewCustomerModal();
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Customers</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newCustomer}>
                        New Customer
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <CustomersTableComponent customers={customersData} onChange={setCustomersData} />
                )}
            </div>

            {isNewCustomerModalOpen && (
                <NewCustomerModal
                    isOpen={isNewCustomerModalOpen}
                    onClose={handleCloseNewCustomerModal}
                    onSave={handleSaveNewCustomer}
                />
            )}
        </div>
    );
};

export default CustomersPage;