import { useState, useEffect, useContext } from 'react'

function List({ setMovie, actor, year, genre }) {
  const [movies, setMovies] = useState([])
  useEffect(() => {
    fetch('/movies')
      .then(res => res.json())
      .then(data => setMovies(data))
  }, [])

  const [aMovies, setAMovies] = useState([])
  useEffect(() => {
    fetch(`/actors/${actor}`)
      .then(res => res.json())
      .then(data => setAMovies(data))
  }, [actor])

  const [gMovies, setGMovies] = useState([])
  useEffect(() => {
    fetch(`/genres/${genre}`)
      .then(res => res.json())
      .then(data => setGMovies(data))
  }, [genre])

  const [yMovies, setYMovies] = useState([])
  useEffect(() => {
    fetch(`/years/${year}`)
      .then(res => res.json())
      .then(data => setYMovies(data))
  }, [year])

  let movieList = movies
  if (actor == null && year == null && genre == null) {
    movieList = movies
  }
  else if (actor !== null) {
    movieList = aMovies
  }
  else if (year !== null) {
    movieList = yMovies
  }
  else if (genre !== null) {
    movieList = gMovies
  }
  else {
    movieList = movies
  }

  return (
    <>
      <h3>List</h3>
      <div id="listDiv">
        {movieList.map((movie, index) =>
          <div class="movieDiv" key={index} onClick={() => setMovie([movie.Key, movie.Title, movie.Genre, movie.Actors, movie.Year, movie.Runtime, movie.Revenue])}>
            <p class="titleP">{movie.Title}</p>
            {(actor !== null) && <p>{movie.Actor}</p>}
            {(genre !== null) && <p>{movie.Genre}</p>}
            {(year == null) ? <p>{movie.Year}</p> : <p>{movie.Key}</p>}
          </div>)}
      </div>
    </>
  )
}

export default List
