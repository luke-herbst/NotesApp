package iu.c323.fall2024.project7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import iu.c323.fall2024.project7.databinding.FragmentLoginBinding

private const val TAG = "LoginFragment"
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val auth = FirebaseAuth.getInstance()

        updateUi()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
                if(task.isSuccessful){
                    Toast.makeText(this.context, "Success!", Toast.LENGTH_SHORT).show()
                    goToNotesScreen()
                }else{
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnSignUp.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    goToNotesScreen()
                } else {
                    Log.e(TAG, "createUserWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Sign-up failed", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.btnLogout.setOnClickListener {
            handleLogout()
            updateUi()
        }
        binding.btnToNotes.setOnClickListener{
            goToNotesScreen()
        }
        return binding.root
    }

    private fun goToNotesScreen(){
        this.findNavController().navigate(R.id.noteListFragment)
    }

    private fun handleLogout() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
    }

    private fun updateUi(){
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            binding.btnLogin.visibility = View.GONE
            binding.btnSignUp.visibility = View.GONE
            binding.etEmail.visibility = View.GONE
            binding.etPassword.visibility = View.GONE
            binding.btnLogout.visibility = View.VISIBLE
            binding.btnToNotes.visibility = View.VISIBLE
        } else{
            binding.btnLogin.visibility = View.VISIBLE
            binding.btnSignUp.visibility = View.VISIBLE
            binding.etEmail.visibility = View.VISIBLE
            binding.etPassword.visibility = View.VISIBLE
            binding.btnLogout.visibility = View.GONE
            binding.btnToNotes.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}