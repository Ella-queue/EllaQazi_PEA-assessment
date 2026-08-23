import { useState, useEffect, useContext } from 'react'
import Actor from './Actor.jsx'
import Genre from './Genre.jsx'

function Movie({ Key }) {
  const [movie, setMovie] = useState([])
    useEffect(() => {
        fetch(`/movies/${Key}`)
          .then(res => res.json())
          .then(data => setMovie(data))
    }, [])
  return (
    <>
    <div class="movieDiv" id="singleMovieDiv">
    <p class="titleP">{ movie.Title }</p>
    <p>{ movie.Year }</p>
    <p>{ movie.Runtime }</p>
    <p>{ movie.Revenue }</p>
    {/* <ul><Genre genreList={ movie.Genre } /></ul>
    <ul><Actor actorList={ movie.Actors } /></ul> */}
    </div>
    </>
  )
}

export default Movie
