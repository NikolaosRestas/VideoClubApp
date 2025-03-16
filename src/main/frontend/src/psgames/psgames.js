import Loader from "../loader/loader";
import React, {useEffect, useState} from 'react';
import {Button} from "@mui/material";
import PsGamesTableComponent from "./PsGamesTableComponent";
import NewPsGameModal from "./NewPsGameModal";

const PsGamesPage=()=>{
    const [psgamesData,setpsgamesData] = useState([]);
    const [isLoading,setIsLoading] = useState(true);
    const [isNewPsGameModalOpen,setNewPsGameModal] = useState(false);


    useEffect(() => {
        fetch('/PsGames')
            .then(response => response.json())
            .then(data => {
                setpsgamesData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching psgames:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newPsGame=()=>{
        setNewPsGameModal(true);
    }

    const handleCloseNewPsGameModal=()=>{
        setNewPsGameModal(false);
    }

    const handleSaveNewPsGame = psgame =>{
        setpsgamesData(prevpsgames=>[...prevpsgames,psgame]);
        handleCloseNewPsGameModal();
    }

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">PsGames</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newPsGame}>
                        New PsGame
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <PsGamesTableComponent PsGames={psgamesData} onChange={setpsgamesData} />
                )}
            </div>

            {isNewPsGameModalOpen && (
                <NewPsGameModal
                    isOpen={isNewPsGameModalOpen}
                    onClose={handleCloseNewPsGameModal}
                    onSave={handleSaveNewPsGame}
                />
            )}
        </div>
    );
};

export default PsGamesPage;