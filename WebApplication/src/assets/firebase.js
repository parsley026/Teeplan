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
