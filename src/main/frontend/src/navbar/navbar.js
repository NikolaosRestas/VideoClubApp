import React from 'react';
import { AppBar, Button, Toolbar, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <AppBar position="sticky">
      <Toolbar variant="dense">
        {/* Logo/Title of the App */}
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          VideoClub App
        </Typography>

        {/* Navigation Links */}
        <Button color="inherit" component={Link} to="/homePage">
          Home
        </Button>
      </Toolbar>
    </AppBar>
  );
}



