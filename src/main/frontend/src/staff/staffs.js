import React, {useEffect, useState} from 'react';
import StaffsTableComponent from "./StaffsTableComponent";
import {Button} from "@mui/material";
import NewStaffModal from "./NewStaffModal";
import Loader from "../loader/loader";

const StaffsPage = () => {
    const [staffsData, setStaffsData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isNewStaffModalOpen, setNewStaffModalOpen] = useState(false);

    useEffect(() => {
        fetch('/staff')
            .then(response => response.json())
            .then(data => {
                setStaffsData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching staffs:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newStaff = () => {
        setNewStaffModalOpen(true);
    };

    const handleCloseNewStaffModal = () => {
        setNewStaffModalOpen(false);
    };

    const handleSaveNewStaff = staff => {
        setStaffsData(prevStaffs => [...prevStaffs, staff]);
        handleCloseNewStaffModal();
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Staffs</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newStaff}>
                        New Staff
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <StaffsTableComponent staffs={staffsData} onChange={setStaffsData} />
                )}
            </div>

            {isNewStaffModalOpen && (
                <NewStaffModal
                    isOpen={isNewStaffModalOpen}
                    onClose={handleCloseNewStaffModal}
                    onSave={handleSaveNewStaff}
                />
            )}
        </div>
    );
};

export default StaffsPage;