import 'package:flutter/material.dart';
import 'login_screen.dart';
import 'request_escort_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('SafeWalk'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              Navigator.pushAndRemoveUntil(
                context,
                MaterialPageRoute(
                  builder: (context) => const LoginScreen(),
                ),
                (route) => false,
              );
            },
          ),
        ],
      ),

      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [

            const Text(
              'Welcome to SafeWalk 👋',
              style: TextStyle(
                fontSize: 25,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 8),

            const Text(
              'Stay connected and travel safely.',
              style: TextStyle(
                color: Colors.grey,
                fontSize: 16,
              ),
            ),

            const SizedBox(height: 30),

            _buildFeatureCard(
              context,
              icon: Icons.directions_walk,
              title: 'Request an Escort',
              subtitle: 'Ask a volunteer to accompany you.',
            ),

            _buildFeatureCard(
              context,
              icon: Icons.volunteer_activism,
              title: 'Volunteer',
              subtitle: 'Help another student travel safely.',
            ),

            _buildFeatureCard(
              context,
              icon: Icons.shield_outlined,
              title: 'Active Journey',
              subtitle: 'Monitor your current escort journey.',
            ),

            _buildFeatureCard(
              context,
              icon: Icons.warning_amber,
              title: 'Safety Report',
              subtitle: 'Report an unsafe location or incident.',
            ),

            _buildFeatureCard(
              context,
              icon: Icons.notifications_active,
              title: 'Safety Alerts',
              subtitle: 'View community safety alerts.',
            ),

            _buildFeatureCard(
              context,
              icon: Icons.map_outlined,
              title: 'Safety Map',
              subtitle: 'View high-risk areas around the city.',
            ),

            const SizedBox(height: 20),

            SizedBox(
              width: double.infinity,
              height: 60,
              child: ElevatedButton.icon(
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red,
                  foregroundColor: Colors.white,
                ),
                onPressed: () {
                  _showSOSDialog(context);
                },
                icon: const Icon(Icons.emergency),
                label: const Text(
                  'EMERGENCY SOS',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildFeatureCard(
    BuildContext context, {
    required IconData icon,
    required String title,
    required String subtitle,
  }) {
    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      child: ListTile(
        contentPadding: const EdgeInsets.all(12),

        leading: CircleAvatar(
          child: Icon(icon),
        ),

        title: Text(
          title,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),

        subtitle: Text(subtitle),

        trailing: const Icon(Icons.arrow_forward_ios, size: 18),

        onTap: () {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('$title will be implemented next.'),
            ),
          );
        },
      ),
    );
  }

  void _showSOSDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Emergency SOS'),
          content: const Text(
            'Emergency functionality will be connected to the SafeWalk backend.',
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text('SEND SOS'),
            ),
          ],
        );
      },
    );
  }
}