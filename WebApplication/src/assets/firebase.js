import { initializeApp } from "firebase/app";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import { getDatabase, ref, get, child } from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyAcDPL2ibubVLvP7wTkAWL6BLAeB3qEdPM",
  authDomain: "teeplan-afede.firebaseapp.com",
  databaseURL: "https://teeplan-afede-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "teeplan-afede",
  storageBucket: "teeplan-afede.appspot.com",
  messagingSenderId: "211842582548",
  appId: "1:211842582548:web:1154b8e7ed6fe4a6feff01",
  measurementId: "G-4B1P4TSWGQ"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Other Features
const auth = getAuth(app);
const database = getDatabase(app);

// Initialize registerUser
// const register = document.getElementById('register');
// register.addEventListener("click", function(event) {
//   event.preventDefault();
  
//   const first_name = "none";
//   const last_name = "none";
//   const is_admin = false;
//   const email = document.getElementById("email_input").value;
//   const password = document.getElementById("password_input").value;

//   const data = {
//     first_name : first_name,
//     last_name : last_name,
//     is_admin : is_admin,
//     email : email,
//     password : password
//   };

//   createUserWithEmailAndPassword(auth, email, password)
//   .then((credentials) => {
//     const ID = auth.currentUser.uid;
//     alert();
//     set(ref(database, 'users/' + ID), data)
//   })
//   .catch((error) => {
//     const errorCode = error.code;
//     const errorMessage = error.message;
//   });
// })

// Initialize loginAdmin
export function login(email, password, callback) {
  signInWithEmailAndPassword(auth, email, password)
    .then((credentials) => {
      get(child(ref(database), 'users/' + credentials.user.uid)).then((snapshot) => {
        const data = snapshot.val();
        if (snapshot.exists() && data.is_admin === true) {
          callback(true);
        } else {
          callback(false);
        }
      });
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      
      callback(false);
      console.error(errorCode + errorMessage);
    });
}

// Initialize getUsers
export async function getUsers() {
  try {
    const snapshot = await get(child(ref(database), 'users'));
    const users = [];
    if (snapshot.exists()) {
      snapshot.forEach((childSnapshot) => {
        users.push(childSnapshot.val());
      });
    }
    return users;
  } catch (error) {
    const errorCode = error.code;
    const errorMessage = error.message;

    console.error("getUsers faild")
    console.error(errorCode + errorMessage);
    return [];
  }
}

// Initialize getCoupons
export async function getCoupons() {
  try {
    const snapshot = await get(child(ref(database), 'coupons'));
    const coupons = [];
    if (snapshot.exists()) {
      snapshot.forEach((childSnapshot) => {
        coupons.push(childSnapshot.val());
      });
    }
    return coupons;
  } catch (error) {
    const errorCode = error.code;
    const errorMessage = error.message;

    console.error("getUsers faild")
    console.error(errorCode + errorMessage);
    return [];
  }
}

// Initialize getEvents
export async function getEvents() {
  try {
    const snapshot = await get(child(ref(database), 'events'));
    const events = [];
    if (snapshot.exists()) {
      snapshot.forEach((childSnapshot) => {
        events.push(childSnapshot.val());
      });
    }
    return events;
  } catch (error) {
    const errorCode = error.code;
    const errorMessage = error.message;

    console.error("getEvents faild")
    console.error(errorCode + errorMessage);
    return [];
  }
}

