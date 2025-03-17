import React, {useEffect, useState} from 'react';
import {Button} from "@mui/material";
import PsGamesTableComponent from "./PsGamesTableComponent";
import NewPsGameModal from "./NewPsGameModal";

const PsGamesPage=()=>{
    const [psgamesData,setpsgamesData] = useState([]);
    const [isNewPsGameModalOpen,setNewPsGameModal] = useState(false);


    useEffect(() => {
        // Function to fetch customers data from the API
        fetch('/PsGames')
            .then(response => response.json())
            .then(data => {
                setpsgamesData(data);
            });

        return () => {
            // Any cleanup code can go here
        };
    }, []);

    useEffect(() => {
        setpsgamesData(psgamesData); // Update local state when the clientData prop changes
    }, [psgamesData]);

    function newPsGame(){
        setNewPsGameModal(true);
    }

    const handleCloseNewPsGameModal=()=>{
        setNewPsGameModal(false);
    }

    const updatePsGames = (psgame) => {
        console.log('psgames : ',psgame);
        console.log('psgames: ',psgamesData);

        setpsgamesData(prevPsGames=>[...prevPsGames,psgame]);
        console.log('PsGames meta: ', psgamesData);
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">PsGames</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newPsGame}>
                        New PsGame
                    </Button>
                </div>


                 <PsGamesTableComponent PsGames={psgamesData} onChange={setpsgamesData} />

            </div>

            {isNewPsGameModalOpen && (
                <NewPsGameModal
                    isOpen={isNewPsGameModalOpen}
                    onClose={handleCloseNewPsGameModal}
                    onSave={updatePsGames}
                />
            )}
        </div>
    );
};

export default PsGamesPage;