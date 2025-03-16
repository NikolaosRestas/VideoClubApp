import {Component} from 'react';

class MoviesList extends Component{
    constructor(props){
        super(props);
        this.state = {clients:[]};
        this.remove = this.remove.bind(this);
    }
    async componentDidMount(){
        fetch('http://localhost:8080/movies')
            .then(response => response.json())
            .then(data => {
                this.setState({clients: data});
            });
    }
}

export default MoviesList;