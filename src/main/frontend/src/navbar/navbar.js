import React from 'react';
import {AppBar, Button, IconButton, Toolbar, Typography} from '@mui/material';
import {Link} from 'react-router-dom';
import MenuIcon from '@mui/icons-material/Menu';

export default function Navbar() {
    return (
        <div className="bg-gradient-to-r from-blue-500 via-blue-600 to-indigo-600">
            <AppBar position="static">
                <Toolbar variant="dense">
                    <IconButton edge="start" className="mr-2 text-white" color="inherit" aria-label="menu">
                        <MenuIcon/>
                    </IconButton>
                    <Typography variant="h6" className="text-white">
                        VideClub App
                    </Typography>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/homePage">Home</Button>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/videoClubs">Videoclubs</Button>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/staff">Staff</Button>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/customers">Customers</Button>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/cd">Cds</Button>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/PsGames">PsGames</Button>
                    <Button color="inherit" className="text-gray-500" component={Link} to="/movies">Movies</Button>
                </Toolbar>
            </AppBar>
        </div>
    );
}

