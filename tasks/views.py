from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from django.http import JsonResponse
from .models import Task
from .forms import TaskForm


@login_required
def task_list(request):
    tasks = Task.objects.filter(user=request.user)

    # FILTER LOGIC — THIS WAS MISSING
    filter_type = request.GET.get('filter')

    if filter_type == 'completed':
        tasks = tasks.filter(is_completed=True)
    elif filter_type == 'pending':
        tasks = tasks.filter(is_completed=False)
    # 'all' or no filter → show everything

    tasks = tasks.order_by('is_completed', '-priority', 'due_date')

    return render(request, 'tasks/task_list.html', {'tasks': tasks})


@login_required
def task_create(request):
    if request.method == 'POST':
        form = TaskForm(request.POST)
        if form.is_valid():
            task = form.save(commit=False)
            task.user = request.user
            task.save()
            messages.success(request, f'Task "{task.title}" created successfully!')
            return redirect('task_list')
        else:
            messages.error(request, 'Please correct the errors below.')
    else:
        form = TaskForm()
    return render(request, 'tasks/task_form.html', {'form': form})


@login_required
def task_edit(request, pk):
    task = get_object_or_404(Task, pk=pk, user=request.user)
    if request.method == 'POST':
        form = TaskForm(request.POST, instance=task)
        if form.is_valid():
            form.save()
            messages.success(request, f'Task "{task.title}" updated!')
            return redirect('task_list')
        else:
            messages.error(request, 'Please fix the errors.')
    else:
        form = TaskForm(instance=task)
    return render(request, 'tasks/task_form.html', {'form': form, 'task': task})


@login_required
def task_delete(request, pk):
    task = get_object_or_404(Task, pk=pk, user=request.user)
    if request.method == 'POST':
        title = task.title
        task.delete()
        messages.success(request, f'Task "{title}" deleted permanently.')
        return redirect('task_list')
    return render(request, 'tasks/task_list.html', {'tasks': Task.objects.filter(user=request.user)})


@login_required
def toggle_task(request, pk):
    if request.method == 'POST':
        task = get_object_or_404(Task, pk=pk, user=request.user)
        task.is_completed = not task.is_completed
        task.save()
        return JsonResponse({'status': 'ok', 'is_completed': task.is_completed})
    return JsonResponse({'status': 'error'}, status=400)