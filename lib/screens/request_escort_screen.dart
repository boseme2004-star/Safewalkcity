import 'package:flutter/material.dart';

class RequestEscortScreen extends StatefulWidget {
  const RequestEscortScreen({super.key});

  @override
  State<RequestEscortScreen> createState() =>
      _RequestEscortScreenState();
}

class _RequestEscortScreenState
    extends State<RequestEscortScreen> {
  final pickupController = TextEditingController();
  final destinationController = TextEditingController();
  final notesController = TextEditingController();

  DateTime? selectedDate;
  TimeOfDay? selectedTime;

  bool isSubmitting = false;

  @override
  void dispose() {
    pickupController.dispose();
    destinationController.dispose();
    notesController.dispose();
    super.dispose();
  }

  Future<void> selectDate() async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(
        const Duration(days: 365),
      ),
    );

    if (picked != null) {
      setState(() {
        selectedDate = picked;
      });
    }
  }

  Future<void> selectTime() async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
    );

    if (picked != null) {
      setState(() {
        selectedTime = picked;
      });
    }
  }

  String get formattedDate {
    if (selectedDate == null) {
      return 'Select date';
    }

    return '${selectedDate!.year}-'
        '${selectedDate!.month.toString().padLeft(2, '0')}-'
        '${selectedDate!.day.toString().padLeft(2, '0')}';
  }

  String get formattedTime {
    if (selectedTime == null) {
      return 'Select time';
    }

    return '${selectedTime!.hour.toString().padLeft(2, '0')}:'
        '${selectedTime!.minute.toString().padLeft(2, '0')}:00';
  }

  void submitRequest() {
    if (pickupController.text.trim().isEmpty) {
      showMessage('Please enter your pickup location.');
      return;
    }

    if (destinationController.text.trim().isEmpty) {
      showMessage('Please enter your destination.');
      return;
    }

    if (selectedDate == null) {
      showMessage('Please select a date.');
      return;
    }

    if (selectedTime == null) {
      showMessage('Please select a time.');
      return;
    }

    setState(() {
      isSubmitting = true;
    });

    // Backend connection will be added next.
    Future.delayed(const Duration(milliseconds: 500), () {
      if (!mounted) return;

      setState(() {
        isSubmitting = false;
      });

      showMessage(
        'Request information is ready to be sent.',
      );
    });
  }

  void showMessage(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Request an Escort'),
      ),

      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(20),

          child: Column(
            crossAxisAlignment:
                CrossAxisAlignment.stretch,

            children: [

              const Icon(
                Icons.directions_walk,
                size: 70,
              ),

              const SizedBox(height: 15),

              const Text(
                'Request an Escort',
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: 27,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 8),

              const Text(
                'Tell us where and when you need '
                'someone to accompany you.',
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 15,
                ),
              ),

              const SizedBox(height: 30),

              // PICKUP
              TextField(
                controller: pickupController,
                textInputAction:
                    TextInputAction.next,

                decoration: const InputDecoration(
                  labelText: 'Pickup Location',
                  hintText: 'e.g. Molyko',
                  prefixIcon: Icon(
                    Icons.location_on_outlined,
                  ),
                  border: OutlineInputBorder(),
                ),
              ),

              const SizedBox(height: 16),

              // DESTINATION
              TextField(
                controller: destinationController,
                textInputAction:
                    TextInputAction.next,

                decoration: const InputDecoration(
                  labelText: 'Destination',
                  hintText: 'e.g. Buea Town',
                  prefixIcon: Icon(
                    Icons.flag_outlined,
                  ),
                  border: OutlineInputBorder(),
                ),
              ),

              const SizedBox(height: 16),

              // DATE
              InkWell(
                onTap: selectDate,

                child: InputDecorator(
                  decoration:
                      const InputDecoration(
                    labelText: 'Date',
                    prefixIcon: Icon(
                      Icons.calendar_today,
                    ),
                    border: OutlineInputBorder(),
                  ),

                  child: Text(
                    formattedDate,
                    style: TextStyle(
                      color: selectedDate == null
                          ? Colors.grey
                          : Colors.black,
                    ),
                  ),
                ),
              ),

              const SizedBox(height: 16),

              // TIME
              InkWell(
                onTap: selectTime,

                child: InputDecorator(
                  decoration:
                      const InputDecoration(
                    labelText: 'Time',
                    prefixIcon: Icon(
                      Icons.access_time,
                    ),
                    border: OutlineInputBorder(),
                  ),

                  child: Text(
                    formattedTime,
                    style: TextStyle(
                      color: selectedTime == null
                          ? Colors.grey
                          : Colors.black,
                    ),
                  ),
                ),
              ),

              const SizedBox(height: 16),

              // NOTES
              TextField(
                controller: notesController,
                maxLines: 4,

                decoration: const InputDecoration(
                  labelText: 'Notes',
                  hintText:
                      'Additional information (optional)',
                  prefixIcon: Icon(
                    Icons.notes_outlined,
                  ),
                  border: OutlineInputBorder(),
                  alignLabelWithHint: true,
                ),
              ),

              const SizedBox(height: 30),

              SizedBox(
                height: 55,

                child: ElevatedButton.icon(
                  onPressed:
                      isSubmitting
                          ? null
                          : submitRequest,

                  icon: isSubmitting
                      ? const SizedBox(
                          width: 20,
                          height: 20,
                          child:
                              CircularProgressIndicator(
                            strokeWidth: 2,
                          ),
                        )
                      : const Icon(Icons.send),

                  label: Text(
                    isSubmitting
                        ? 'Submitting...'
                        : 'REQUEST ESCORT',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),

              const SizedBox(height: 20),

              const Text(
                'Your request will initially have a '
                'PENDING status until a volunteer accepts it.',
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 13,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}