import { useState, useEffect, useContext } from 'react'
import List from './List.jsx'

function YearSelect({ setMovie }) {
  // Props Drilling
     const [year, setYear] = useState("")
    const setNewYear = () => {
      setYear(document.getElementById("year").value)
    }
  return (
    <>
    <h2>Year Select</h2>
    <div class="inputLabelDiv">
    <label for="year">Year:</label>
    <input type="text" inputmode="numeric" id="year" name="year" onChange={setNewYear}></input>
    <List actor={null} year={year} genre={null}/>
    </div>
    </>
  )
}

export default YearSelect
