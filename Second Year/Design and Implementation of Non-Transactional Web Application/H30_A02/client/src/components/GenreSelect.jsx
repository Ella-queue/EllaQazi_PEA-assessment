import { useState, useEffect, useContext } from 'react'
import List from './List.jsx'

function GenreSelect({ setMovie }) {
    // Get rid of props drilling
    const [genre, setGenre] = useState("")
    const setNewGenre = () => {
      setGenre(document.getElementById("genre").value)
    }
  return (
    <>
    <h2>Genre Select</h2>
    <div class="inputLabelDiv">
    <label for="genre">Genre Name:</label>
    <input type="text" id="genre" name="genre" onChange={ setNewGenre }></input>
    <List actor={null} year={null} genre={genre}/>
    </div>
    </>
  )
}

export default GenreSelect
