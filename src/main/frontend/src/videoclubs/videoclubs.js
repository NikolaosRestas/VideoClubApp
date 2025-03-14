import React, { useEffect, useState } from 'react';
import VideoclubsTableComponent from './VideoclubsTableComponent';
import { Button } from '@mui/material';
import NewVideoclubModal from './NewVideoclubModal';
import Loader from "../loader/loader";

const VideoclubsPage = () => {
    const [videoclubsData, setVideoclubsData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isNewVideoclubModalOpen, setNewVideoclubModalOpen] = useState(false);

    useEffect(() => {
        fetch('/videoClubs')
            .then(response => response.json())
            .then(data => {
                setVideoclubsData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching videoclubs:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newVideoclub = () => {
        setNewVideoclubModalOpen(true);
    };

    const handleCloseNewVideoclubModal = () => {
        setNewVideoclubModalOpen(false);
    };

    const handleSaveNewVideoclub = videoclub => {
        setVideoclubsData(prevVideoclubs => [...prevVideoclubs, videoclub]);
        handleCloseNewVideoclubModal();
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Videoclubs</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newVideoclub}>
                        New Videoclub
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <VideoclubsTableComponent videoclubs={videoclubsData} onChange={setVideoclubsData} />
                )}
            </div>

            {isNewVideoclubModalOpen && (
                <NewVideoclubModal
                    isOpen={isNewVideoclubModalOpen}
                    onClose={handleCloseNewVideoclubModal}
                    onSave={handleSaveNewVideoclub}
                />
            )}
        </div>
    );
};

export default VideoclubsPage;