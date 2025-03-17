import React, {useEffect, useState} from 'react';
import CdsTableComponent from "./CdsTableComponent";
import {Button} from "@mui/material";
import NewCdModal from "./NewCdModal";

const CdsPage=()=>{
    const [cdsData,setCdsData] = useState([]);
    const [isNewCdModalOpen,setNewCdModalOpen] = useState(false);

    useEffect(() => {
        // Function to fetch customers data from the API
        fetch('/cd')
            .then(response => response.json())
            .then(data => {
                setCdsData(data);
            });

        return () => {
            // Any cleanup code can go here
        };
    }, []);

    useEffect(() => {
       setCdsData(cdsData); // Update local state when the clientData prop changes
    }, [cdsData]);

    function newCd(){
        setNewCdModalOpen(true);
    }

    const handleCloseNewCdModal = () => {
        setNewCdModalOpen(false);
    };

   const updateCds = (cd) => {
        console.log('cds : ',cd);
        console.log('cds: ',cdsData);

        setCdsData(prevCds=>[...prevCds,cd]);
        console.log('Cds meta: ', cdsData);
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Cds</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newCd}>
                        New Cd
                    </Button>
                </div>


                 <CdsTableComponent cds={cdsData} onChange={setCdsData} />

            </div>

            {isNewCdModalOpen && (
                <NewCdModal
                    isOpen={isNewCdModalOpen}
                    onClose={handleCloseNewCdModal}
                    onSave={updateCds}
                />
            )}
        </div>
    );
};
export default CdsPage;