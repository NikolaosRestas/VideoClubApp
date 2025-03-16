import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {Alert,Button} from "@mui/material";
import {useState} from "react";
import EditMovieModal from "./EditMovieModal";

const MoviesTableComponent=({movies,onChange})=>{

    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedClient, setSelectedClient] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    const handleEditModalClose=()=>{
        setIsEditModalOpen(false);
    }

    const handleEditModalOpen=(movie)=>{
        setSelectedClient(movie);
        setIsEditModalOpen(true);
    }

    const handleDelete = (movie) => {
           fetch(`/movies/delete/${movie.id}`, {
               method: 'DELETE',
               headers: { 'Content-Type': 'application/json' },
           })
               .then(response => {
                   if (response.ok) {
                       setIsSuccessfulDelete({ MovieTitle: movie.title });
                       setIsSuccessfulDelete({ MovieYear: movie.year});
                       setTimeout(() => {
                           setIsSuccessfulDelete(false);
                       }, 5000);
                       onChange(movies.filter(c => c.id !== movie.id));
                   }
               });
       };

    return (
        <React.Fragment>
            <TableContainer component={Paper} className="shadow-lg rounded-lg">
                <Table className="min-w-max" aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell className="font-bold">Id</TableCell>
                            <TableCell align="right" className="font-bold">Title</TableCell>
                            <TableCell align="right" className="font-bold">Year</TableCell>
                            <TableCell align="right" className="font-bold">Videoclub</TableCell>
                            <TableCell align="right" className="font-bold">Customer</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {movies.map((movie) => (
                            <TableRow
                                key={movie.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {movie.id}
                                </TableCell>
                                <TableCell align="right" className="py-3">{movie.title}</TableCell>
                                <TableCell align="right" className="py-3">{movie.year}</TableCell>
                                <TableCell align="right" className="py-3">{movie.videoClub.name}</TableCell>
                                <TableCell align="right" className="py-3">{movie.customer.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => handleEditModalOpen(movie)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => handleDelete(movie)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedClient && (
                <EditMovieModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedClient}
                />
            )}

            {isSuccessfulDelete && (
                <div className="relative h-32 flex flex-nowrap">
                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                        <Alert severity="success">
                            The movie {isSuccessfulDelete.MovieTitle} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
};
export default MoviesTableComponent;