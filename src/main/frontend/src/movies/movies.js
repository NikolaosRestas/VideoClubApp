import Loader from "../loader/loader";
import React, {useEffect, useState} from 'react';
import {Button} from "@mui/material";
import MoviesTableComponent from "./MoviesTableComponent";
import NewMovieModal from "./NewMovieModal";

const MoviesPage=()=>{

    const [isLoading,setIsLoading] = useState(true);
    const [moviesData,setmoviesData] = useState([]);
    const [isNewMovieModalOpen,setIsNewMovieModal] = useState(false);

    useEffect(() => {
            fetch('/movies')
                .then(response => response.json())
                .then(data => {
                    setmoviesData(data);
                    setIsLoading(false);
                })
                .catch(error => {
                    console.error('Error fetching movies:', error);
                    setIsLoading(false); // Ensure loading state is updated even on error
                });
        }, []);


    const handleCloseNewMovieModal=()=>{
        setIsNewMovieModal(false);
    };

    const handleSaveNewMovie=(movie)=>{
        setmoviesData(prevmoviesData=>[...prevmoviesData,movie]);
        handleCloseNewMovieModal();
    }

    const newMovie=()=>{
        setIsNewMovieModal(true);
    }

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Movies</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newMovie}>
                        New Movie
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <MoviesTableComponent movies={moviesData} onChange={setmoviesData} />
                )}
            </div>

            {isNewMovieModalOpen && (
                <NewMovieModal
                    isOpen={isNewMovieModalOpen}
                    onClose={handleCloseNewMovieModal}
                    onSave={handleSaveNewMovie}
                />
            )}
        </div>
    );
}
export default MoviesPage;