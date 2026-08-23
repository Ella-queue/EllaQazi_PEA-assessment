import { useState } from 'react'

function Genre({ genreList }) {
  return (
    <>
    {/* {console.log(genreList)} */}
      {genreList.forEach((genre) =>
        <li>{ genre }</li>
      )}
    </>
  )
}

export default Genre
