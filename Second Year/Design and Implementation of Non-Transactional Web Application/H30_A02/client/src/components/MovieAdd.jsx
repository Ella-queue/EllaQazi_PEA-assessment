import { useState, useEffect, useContext } from 'react'

function MovieAdd() {
    const [movies, setMovies] = useState([])
    useEffect(() => {
        fetch('/movies')
          .then(res => res.json())
          .then(data => setMovies(data))
    }, [])

    function addMovie() {
      let title = document.getElementById("title").value
      let genreStr = document.getElementById("genre").value
      let genre = genreStr.split(', ')
      let actorsStr = document.getElementById("actors").value
      let actors = actorsStr.split(', ')
      let year = document.getElementById("year").value
      let runtime = document.getElementById("runtime").value
      let revenue = document.getElementById("revenue").value
      let movieJson = {Title: title}
    }

  return (
    <>
    <h2>Add Movie</h2>
    <form>
    <label for="title">Title:</label>
    <input type="text" id="title" name="title"></input>

    <label for="genre">Genre:</label>
    <input type="text" id="genre" name="genre"></input>

    <label for="actors">Actors:</label>
    <input type="text" id="actors" name="actors"></input>

    <label for="year">Year:</label>
    <input type="text" id="year" name="year"></input>

    <label for="runtime">Runtime:</label>
    <input type="text" id="runtime" name="runtime"></input>

    <label for="revenue">Revenue:</label>
    <input type="text" id="revenue" name="revenue"></input>

    <button id="addBtn" onClick={addMovie}>Add</button>
    </form>
    </>
  )
}

export default MovieAdd
