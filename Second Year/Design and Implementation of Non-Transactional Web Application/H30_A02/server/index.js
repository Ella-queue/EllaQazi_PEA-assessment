const express = require("express")
const fs = require('fs')

const app = express()
const PORT = 8888

app.use(express.json())

app.get("/movies", (req, res) => {
    fs.readFile('./data/movies.json', (err, data) => {
        if (err) {
            console.log(err)
        }
        let moviesObj = JSON.parse(data)
        moviesObj.sort((a, b) => (a.Title > b.Title ? 1 : -1))
        res.json(moviesObj)
    })
})

app.get("/movies/:id", (req, res) => {
    fs.readFile('./data/movies.json', (err, data) => {
        if (err) {
            console.log(err)
        }
        let moviesObj = JSON.parse(data)
        moviesObj.forEach((movie) => {
            if (movie.Key == req.params.id) {
                res.json(movie)
            }
            console.log(movie)
        })
    })
})

app.get("/actors/:name", (req, res) => {
    fs.readFile('./data/movies.json', (err, data) => {
        if (err) {
            console.log(err)
        }
        let allMoviesObj = JSON.parse(data)
        let movieArr = []
        //res.json(moviesObj.find(key == id))
        allMoviesObj.forEach((movie) => {
            movie.Actors.forEach((actor) => {
                if (actor.includes(req.params.name)) {
                    let movieJson = { Title: movie.Title, Year: movie.Year, Id: movie.Key, Actor: actor }
                    movieArr.push(movieJson)
                }
            })
        })
        let moviesJson = JSON.stringify(movieArr)
        let moviesObj = JSON.parse(moviesJson)
        moviesObj.sort((a, b) => (a.Title > b.Title ? 1 : -1))
        res.json(moviesObj)
    })
})

app.get("/years/:year", (req, res) => {
    fs.readFile('./data/movies.json', (err, data) => {
        if (err) {
            console.log(err)
        }
        let allMoviesObj = JSON.parse(data)
        let movieArr = []
        allMoviesObj.forEach((movie) => {
            if (movie.Year == req.params.year) {
                let movieJson = { Title: movie.Title, Id: movie.Key }
                movieArr.push(movieJson)
            }
        })
        let moviesJson = JSON.stringify(movieArr)
        let moviesObj = JSON.parse(moviesJson)
        moviesObj.sort((a, b) => (a.Title > b.Title ? 1 : -1))
        res.json(moviesObj)
    })
})

app.get("/genres/:genre", (req, res) => {
    fs.readFile('./data/movies.json', (err, data) => {
        if (err) {
            console.log(err)
        }
        console.log("This is genre: " + req.params.genre)
        let allMoviesObj = JSON.parse(data)
        let movieArr = []
        allMoviesObj.forEach((movie) => {
            movie.Genre.forEach((genre) => {
                if (genre.toUpperCase().includes((req.params.genre).toUpperCase())) {
                    let movieJson = { Title: movie.Title, Year: movie.Year, Id: movie.Key, Genre: genre }
                    movieArr.push(movieJson)
                }
            })
        })
        let moviesJson = JSON.stringify(movieArr)
        let moviesObj = JSON.parse(moviesJson)
        moviesObj.sort((a, b) => (a.Title > b.Title ? 1 : -1))
        res.json(moviesObj)
        console.log(moviesObj)
    })
})

app.post('/movies', (req, res) => {
    fs.readFile('./data/movies.json', (err, data) => {
        if (err) {
            console.log(err)
        }
        let moviesObj = JSON.parse(data)
        let currentKey = 0
        movies.Obj.forEach((movie) => {
            if (movie.Key > currentKey) {
                currentKey = movie.Key
            }
        })
    })
})

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`)
})