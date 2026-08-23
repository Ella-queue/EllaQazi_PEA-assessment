import { useState, useEffect, useContext } from 'react'
import List from './List.jsx'

function ActorSelect({ setMovie }) {
    // Get rid of props drilling in Actor Select and Year Select
    const [actor, setActor] = useState("")
    const setNewActor = () => {
      setActor(document.getElementById("name").value)
    }
  return (
    <>
    <h2>Actor Select</h2>
    <div class="inputLabelDiv">
    <label for="name">Actor Name:</label>
    <input type="text" id="name" name="name" onChange={ setNewActor }></input>
    <List actor={actor} year={null} genre={null}/>
    </div>
    </>
  )
}

export default ActorSelect
