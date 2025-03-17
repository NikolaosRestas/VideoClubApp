import React, {useEffect, useState} from 'react';
import {Button} from "@mui/material";
import MoviesTableComponent from "./MoviesTableComponent";
import NewMovieModal from "./NewMovieModal";

const MoviesPage=()=>{

    const [moviesData,setMoviesData] = useState([]);
    const [isNewMovieModalOpen,setIsNewMovieModal] = useState(false);

    useEffect(() => {
            fetch('/movies')
                .then(response => response.json())
                .then(data => {
                    setMoviesData(data);
                })
                .catch(error => {
                    console.error('Error fetching movies:', error);
                });
        }, []);

    useEffect(() => {
        setMoviesData(moviesData); // Update local state when the clientData prop changes
    }, [moviesData]);


    function newMovie(){
        setIsNewMovieModal(true);
    }

    const handleCloseNewMovieModal=()=>{
        setIsNewMovieModal(false);
    }

    const updateMovies = (movie) => {
        console.log('movies : ',movie);
        console.log('movies: ',moviesData);

        setMoviesData(prevMovies=>[...prevMovies,movie]);
        console.log('Movies meta: ', moviesData);
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Movies</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newMovie}>
                        New Movie
                    </Button>
                </div>
                  <MoviesTableComponent movies={moviesData} onChange={setMoviesData} />
            </div>

            {isNewMovieModalOpen && (
                <NewMovieModal
                    isOpen={isNewMovieModalOpen}
                    onClose={handleCloseNewMovieModal}
                    onSave={updateMovies}
                />
            )}
        </div>
    );
}
export default MoviesPage;