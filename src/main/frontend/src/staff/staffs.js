import React, {useEffect, useState} from 'react';
import StaffsTableComponent from "./StaffsTableComponent";
import {Button} from "@mui/material";
import NewStaffModal from "./NewStaffModal";

const StaffsPage = () => {
    const [staffsData, setStaffsData] = useState([]);
    const [isNewStaffModalOpen, setNewStaffModalOpen] = useState(false);

    useEffect(() => {
        // Function to fetch customers data from the API
        fetch('/staff')
            .then(response => response.json())
            .then(data => {
                setStaffsData(data);
            });

        return () => {
            // Any cleanup code can go here
        };
    }, []); // Empty dependency array means this effect runs only once when the component mounts

    useEffect(() => {
        setStaffsData(staffsData); // Update local state when the clientData prop changes
    }, [staffsData]);

    function newStaff() {
        setNewStaffModalOpen(true);
    }

    const handleCloseNewStaffModal = () => {
        setNewStaffModalOpen(false);
    };

    const updateStaffs = (staff) => {
        console.log('staffs : ',staff);
        console.log('Staffs: ',staffsData);

        setStaffsData(prevStaffs=>[...prevStaffs,staff]);
        console.log('Customers meta: ', staffsData);
    };

    return (
       <div className="flex justify-center">
                   <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                       <h3 className="text-3xl font-bold mb-4">Staffs</h3>

                       <div className="text-right mb-4">
                           <Button variant="contained" color="primary"
                                   onClick={() => newStaff()}>
                               New Staff
                           </Button>
                       </div>

                       <StaffsTableComponent staffs={staffsData} onChange={setStaffsData}/>
                   </div>

            {isNewStaffModalOpen && (
                <NewStaffModal
                    isOpen={isNewStaffModalOpen}
                    onClose={handleCloseNewStaffModal}
                    onSave={updateStaffs}
                />
            )}
        </div>
    );
};

export default StaffsPage;