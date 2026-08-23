import { useState, useEffect, createContext, useContext } from 'react'
import '../styles/App.css'
import List from './List.jsx'
import Movie from './Movie.jsx'
import ActorSelect from './ActorSelect.jsx'
import YearSelect from './YearSelect.jsx'
import GenreSelect from './GenreSelect.jsx'
import MovieAdd from './MovieAdd.jsx'

function App() {
  let [currentScreen, setScreen] = useState(["list"])
  function setMovie(key) {
    // setScreen("movie")
    setScreen(["movie", key])
  }
  function setList() {
    setScreen(["list"])
  }
  function setASelect() {
    setScreen(["aSelect"])
  }
  function setYSelect() {
    setScreen(["ySelect"])
  }
  function setGSelect() {
    setScreen(["gSelect"])
  }
  function setAdd() {
    setScreen(["add"])
  }
  return (
    <>
    <div id="appDiv">
    <div id="topBar">
    <h1>App</h1>
    <div id="nav">
    <button onClick={setList}>List</button>
    <button onClick={setASelect}>Actor Search</button>
    <button onClick={setYSelect}>Year Search</button>
    <button onClick={setGSelect}>Genre Search</button>
    <button onClick={setAdd}>Add Movie</button>
    </div>
    </div>
    {(currentScreen == "list")? <div class="fullListDiv"><List setMovie={setMovie} actor={null} year={null} genre={null}/></div>: (currentScreen.length == 2)? <div><Movie
    Key={currentScreen[1][0]}
    /></div>:
    (currentScreen == "aSelect")?<div><ActorSelect setMovie={setMovie}/></div>:
    (currentScreen == "ySelect")?<div><YearSelect setMovie={setMovie}/></div>:
    (currentScreen == "gSelect")?<div><GenreSelect setMovie={setMovie}/></div>:
    (currentScreen == "add")? <div><MovieAdd /></div>:
     <div class="fullListDiv"><List setMovie={setMovie} actor={null} year={null} genre={null}/></div>
     }
    </div>
    </>
  )
}

export default App
