import Loader from "../loader/loader";
import React, {useEffect, useState} from 'react';
import CdsTableComponent from "./CdsTableComponent";
import {Button} from "@mui/material";
import NewCdModal from "./NewCdModal";

const CdsPage=()=>{
    const [cdsData,setCdsData] = useState([]);
    const [isLoading,setIsLoading] = useState(true);
    const [isNewCdModalOpen,setNewCdModalOpen] = useState(false);

    useEffect(() => {
        fetch('/cd')
            .then(response => response.json())
            .then(data => {
                setCdsData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching cds:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newCd=()=>{
        setNewCdModalOpen(true);
    }

    const handleCloseNewCdModal = () => {
        setNewCdModalOpen(false);
    };

    const handleSaveNewCd = cd => {
        setCdsData(prevCds => [...prevCds, cd]);
        handleCloseNewCdModal();
    }

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Cds</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newCd}>
                        New Cd
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <CdsTableComponent cds={cdsData} onChange={setCdsData} />
                )}
            </div>

            {isNewCdModalOpen && (
                <NewCdModal
                    isOpen={isNewCdModalOpen}
                    onClose={handleCloseNewCdModal}
                    onSave={handleSaveNewCd}
                />
            )}
        </div>
    );
};
export default CdsPage;